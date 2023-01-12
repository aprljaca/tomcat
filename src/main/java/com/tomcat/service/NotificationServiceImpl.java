package com.tomcat.service;

import com.tomcat.entity.*;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import com.tomcat.repository.*;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    @Lazy
    private FollowService followService;

    private final Mapper mapper;

    @Override
    public NotificationNumbers getNotificationNumber(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        Optional<NotificationEntity> entity = notificationRepository.findByUserId(userEntity.get().getId());
        if (entity.isEmpty()) {
            return new NotificationNumbers(0L, userEntity.get().getId(), 0L, 0L, 0L, 0L);
        }

        NotificationEntity notificationEntity = entity.get();

        return new NotificationNumbers(notificationEntity.getId(), userEntity.get().getId(),
                notificationEntity.getUnreadNotification(), notificationEntity.getUnreadFollow(),
                notificationEntity.getUnreadComment(), notificationEntity.getUnreadLike());
    }

    @Override
    public void increaseUnreadFollow(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        Optional<NotificationEntity> notificationEntity = notificationRepository.findByUserId(userEntity.get().getId());
        if (notificationEntity.isEmpty()) {
            NotificationEntity notificationEntity1 = new NotificationEntity(userEntity.get().getId(), 1L, 1L, 0L, 0L);
            notificationRepository.save(notificationEntity1);
        } else {
            notificationEntity.get().setUnreadNotification(notificationEntity.get().getUnreadNotification() + 1L);
            notificationEntity.get().setUnreadFollow(notificationEntity.get().getUnreadFollow() + 1L);
            notificationRepository.save(notificationEntity.get());
        }
    }

    @Override
    public void increaseUnreadComment(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        Optional<NotificationEntity> notificationEntity = notificationRepository.findByUserId(userEntity.get().getId());
        if (notificationEntity.isEmpty()) {
            NotificationEntity notificationEntity1 = new NotificationEntity(userEntity.get().getId(), 1L, 0L, 1L, 0L);
            notificationRepository.save(notificationEntity1);
        } else {
            notificationEntity.get().setUnreadNotification(notificationEntity.get().getUnreadNotification() + 1L);
            notificationEntity.get().setUnreadComment(notificationEntity.get().getUnreadComment() + 1L);
            notificationRepository.save(notificationEntity.get());
        }
    }

    @Override
    public void increaseUnreadLike(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        Optional<NotificationEntity> notificationEntity = notificationRepository.findByUserId(userEntity.get().getId());
        if (notificationEntity.isEmpty()) {
            NotificationEntity notificationEntity1 = new NotificationEntity(userEntity.get().getId(), 1L, 0L, 0L, 1L);
            notificationRepository.save(notificationEntity1);
        } else {
            notificationEntity.get().setUnreadNotification(notificationEntity.get().getUnreadNotification() + 1L);
            notificationEntity.get().setUnreadLike(notificationEntity.get().getUnreadLike() + 1L);
            notificationRepository.save(notificationEntity.get());
        }
    }

    @Override
    public void resetAllUnreadNotification(User user) throws UserNotFoundException, BadRequestException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        Optional<NotificationEntity> notificationEntity = notificationRepository.findByUserId(userEntity.get().getId());

        if (notificationEntity.isEmpty()) {
            throw new BadRequestException("Bad request!");
        } else {
            notificationEntity.get().setUnreadNotification(0L);
            notificationEntity.get().setUnreadFollow(0L);
            notificationEntity.get().setUnreadComment(0L);
            notificationEntity.get().setUnreadLike(0L);
            notificationRepository.save(notificationEntity.get());
        }
    }

    @Override
    public Notification geNotifications(User user) throws UserNotFoundException {
        return new Notification(getUnreadFollowList(user), unreadCommentList(user), unreadLikeList(user));
    }

    public List<ProfileInformation> getUnreadFollowList(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        List<FollowEntity> followList = followRepository.findAllByFollowingId(userEntity.get().getId());
        NotificationNumbers notificationNumbers = getNotificationNumber(user);
        int unreadFollow = Math.toIntExact(notificationNumbers.getUnreadFollow());
        if (unreadFollow == 0) {
            unreadFollow = followList.size() / 3;
        }

        List<FollowEntity> unreadFollowEntityList = followList.subList(followList.size() - unreadFollow, followList.size());

        List<UserEntity> userEntities = new ArrayList<>();

        for (FollowEntity followEntity : unreadFollowEntityList) {
            userEntities.add(userRepository.findById(followEntity.getFollowerId()).get());
        }

        List<ProfileInformation> returnFollowList = new ArrayList<>();
        for (UserEntity userEntity1 : userEntities) {
            String profileImage = imageService.downloadImage(userEntity1.getId());
            returnFollowList.add(mapper.mapUserEntityToProfileInformation(userEntity1, profileImage, followService.getFollowersNumber(userEntity1.getId()), followService.getFollowingNumber(userEntity1.getId())));
        }
        Collections.reverse(returnFollowList);
        return returnFollowList;
    }

    public List<NotificationInformation> unreadCommentList(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        List<CommentEntity> commentList = commentRepository.findAllByOwner(userEntity.get().getId());
        NotificationNumbers notificationNumbers = getNotificationNumber(user);
        int unreadComment = Math.toIntExact(notificationNumbers.getUnreadComment());
        if (unreadComment == 0) {
            unreadComment = commentList.size() / 3;
        }

        List<CommentEntity> unreadCommentEntityList = commentList.subList(commentList.size() - unreadComment, commentList.size());

        List<Long> postIdsList = new ArrayList<>();
        List<UserEntity> userEntities = new ArrayList<>();
        List<NotificationInformation> returnCommentList = new ArrayList<>();

        for (CommentEntity commentEntity : unreadCommentEntityList) {
            userEntities.add(userRepository.findById(commentEntity.getUserId()).get());
            postIdsList.add(commentEntity.getPostId());
        }

        for (int i = 0; i < userEntities.size(); i++) {
            String profileImage = imageService.downloadImage(userEntities.get(i).getId());
            returnCommentList.add(mapper.mapUserEntityToNotificationInformation(userEntities.get(i), profileImage, postIdsList.get(i)));
        }
        Collections.reverse(returnCommentList);
        return returnCommentList;
    }

    public List<NotificationInformation> unreadLikeList(User user) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        List<LikeEntity> likeList = likeRepository.findAllByOwner(userEntity.get().getId());
        NotificationNumbers notificationNumbers = getNotificationNumber(user);
        int unreadLike = Math.toIntExact(notificationNumbers.getUnreadLike());
        //ako nema novih obavjesti vrati trecinu vec procitanih
        if (unreadLike == 0) {
            unreadLike = likeList.size() / 3;
        }

        List<LikeEntity> unreadLikeEntityList = likeList.subList(likeList.size() - unreadLike, likeList.size());

        List<Long> postIdsList = new ArrayList<>();
        List<UserEntity> userEntities = new ArrayList<>();
        List<NotificationInformation> returnLikeList = new ArrayList<>();

        for (LikeEntity likeEntity : unreadLikeEntityList) {
            userEntities.add(userRepository.findById(likeEntity.getUserId()).get());
            postIdsList.add(likeEntity.getPostId());
        }
        for (int i = 0; i < userEntities.size(); i++) {
            String profileImage = imageService.downloadImage(userEntities.get(i).getId());
            returnLikeList.add(mapper.mapUserEntityToNotificationInformation(userEntities.get(i), profileImage, postIdsList.get(i)));
        }
        Collections.reverse(returnLikeList);
        return returnLikeList;
    }


}
