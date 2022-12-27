package com.tomcat.service;

import com.tomcat.entity.CommentEntity;
import com.tomcat.entity.LikeEntity;
import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import com.tomcat.repository.CommentRepository;
import com.tomcat.repository.LikeRepository;
import com.tomcat.repository.PostRepository;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Mapper mapper;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final FollowService followService;

    @Override
    public CreatePostResponse createPost(User user, CreatePostRequest request) throws BadRequestException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (request.getText().isEmpty()) {
            throw new BadRequestException("Post text can not be empty!");
        }
        PostEntity post = new PostEntity(request.getText(), OffsetDateTime.now(), userEntity.get().getId());
        postRepository.save(post);
        return mapper.mapPostEntityToPostDto(post);
    }

    @Override
    public void setLike(User user, Post post) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (!isLiked(user, post.getPostId())) {
            LikeEntity like = new LikeEntity(post.getPostId(), userEntity.get().getId(), OffsetDateTime.now());
            likeRepository.save(like);
        }
    }

    @Override
    public void commentPost(CommentRequest request, UserEntity userEntity) throws BadRequestException {
        if (request.getText().isEmpty()) {
            throw new BadRequestException("Comment can not be empty!");
        }
        CommentEntity comment = new CommentEntity(request.getPostId(), userEntity.getId(), request.getText(), OffsetDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public Boolean isLiked(User user, Long postId) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        Optional<LikeEntity> likeEntity = likeRepository.findLike(userEntity.get().getId(), postId);
        return likeEntity.isPresent();
    }

    @Override
    public void setDislike(User user, Post post) throws BadRequestException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (!isLiked(user, post.getPostId())) {
            throw new BadRequestException("Bad request!");
        }
        Optional<LikeEntity> likeEntity = likeRepository.findLike(userEntity.get().getId(), post.getPostId());
        likeRepository.delete(likeEntity.get());
    }

    @Override
    public List<Post> getFollowingPosts(User user) {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());

        List<ProfileInformation> followingProfiles = followService.getFollowing(userEntity.get().getId());

        List<Long> followingUsersIds = new ArrayList<>();

        for (ProfileInformation p : followingProfiles) {
            followingUsersIds.add(p.getUserId());
        }

        List<PostEntity> postEntitie = postRepository.findyAllByUserIdList(followingUsersIds);
        List<Post> postList = new ArrayList<>();

        for (PostEntity postEntity : postEntitie) {
            List<LikeEntity> likeEntityList = likeRepository.findAllByPostId(postEntity.getId());
            List<CommentEntity> commentEntityList = commentRepository.findAllByPostId(postEntity.getId());

            List<CommentInformation> commentInformationList = new ArrayList<>();
            for (CommentEntity commentEntity : commentEntityList) {
                Optional<UserEntity> commentBy = userRepository.findById(commentEntity.getUserId());
                commentInformationList
                        .add(mapper.mapCommentEntityToCommentInformationDto(commentEntity, commentBy.get().getFirstName(), commentBy.get().getLastName(),
                                imageService.downloadImage(commentEntity.getUserId()), calculateDuration(commentEntity.getCreatedTime())));
            }

            Optional<UserEntity> postBy = userRepository.findById(postEntity.getUserId());
            Post post = new Post(postEntity.getId(), postEntity.getUserId(), postBy.get().getFirstName(), postBy.get().getLastName(), postEntity.getText(),
                    calculateDuration(postEntity.getCreatedTime()), likeEntityList.size(), commentEntityList.size(), imageService.downloadImage(postBy.get().getId()), commentInformationList,
                    isLiked(user, postEntity.getId()));

            postList.add(post);
        }
        Collections.reverse(postList);
        return postList;
    }


    @Override
    public List<Post> getProfilePosts(User loggedUser, Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        List<PostEntity> postEntitie = postRepository.findAllByUserId(userId);
        List<Post> postList = new ArrayList<>();

        for (PostEntity postEntity : postEntitie) {
            List<LikeEntity> likeEntityList = likeRepository.findAllByPostId(postEntity.getId());
            List<CommentEntity> commentEntityList = commentRepository.findAllByPostId(postEntity.getId());

            List<CommentInformation> commentInformationList = new ArrayList<>();
            for (CommentEntity commentEntity : commentEntityList) {
                Optional<UserEntity> commentBy = userRepository.findById(commentEntity.getUserId());
                commentInformationList
                        .add(mapper.mapCommentEntityToCommentInformationDto(commentEntity, commentBy.get().getFirstName(), commentBy.get().getLastName(),
                                imageService.downloadImage(commentEntity.getUserId()), calculateDuration(commentEntity.getCreatedTime())));
            }

            Post post = new Post(postEntity.getId(), postEntity.getUserId(), userEntity.get().getFirstName(), userEntity.get().getLastName(), postEntity.getText(),
                    calculateDuration(postEntity.getCreatedTime()), likeEntityList.size(), commentEntityList.size(), imageService.downloadImage(userId), commentInformationList,
                    isLiked(loggedUser, postEntity.getId()));

            postList.add(post);
        }
        Collections.reverse(postList);
        return postList;
    }

    @Override
    public String calculateDuration(OffsetDateTime time) {
        OffsetDateTime now = OffsetDateTime.now();

        if (ChronoUnit.SECONDS.between(time, now) < 60) {
            return "a few moments ago";
        } else if (ChronoUnit.MINUTES.between(time, now) < 60) {
            return ChronoUnit.MINUTES.between(time, now) + " minutes ago";
        } else if (ChronoUnit.HOURS.between(time, now) < 24) {
            return ChronoUnit.HOURS.between(time, now) + " hours ago";
        } else if (ChronoUnit.DAYS.between(time, now) < 7) {
            return ChronoUnit.DAYS.between(time, now) + " days ago";
        }
        return ChronoUnit.WEEKS.between(time, now) + " weeks ago";
    }
}
