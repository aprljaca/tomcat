package com.tomcat.service;

import com.tomcat.entity.FollowEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;
import com.tomcat.model.User;
import com.tomcat.repository.FollowRepository;
import com.tomcat.repository.ImageRepository;
import com.tomcat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public void followUser(User user, Long userId) throws BadRequestException, UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by username!");
        }
        FollowEntity followEntity = new FollowEntity(userEntity.get().getId(), userId);
        if (isFollowing(user, userId)) {
            throw new BadRequestException("Bad request!");
        }
        followRepository.save(followEntity);
    }

    @Override
    public void unFollowUser(User user, Long userId) throws BadRequestException, UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by username!");
        }
        Optional<FollowEntity> followEntity = followRepository.findFollower(userEntity.get().getId(), userId);
        if (followEntity.isEmpty()) {
            throw new BadRequestException("Bad request!");
        }
        followRepository.delete(followEntity.get());
    }

    @Override
    public Boolean isFollowing(User user, Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by username!");
        }
        Optional<FollowEntity> followEntity = followRepository.findFollower(userEntity.get().getId(), userId);
        return followEntity.isPresent();
    }

    @Override
    public List<ProfileInformation> getFollowers(Long userId) {
        List<FollowEntity> followEntities = followRepository.findAllByFollowingId(userId);

        List<ProfileInformation> profileInformations = new ArrayList<>();

        for (FollowEntity followEntity : followEntities) {
            Optional<UserEntity> userEntity = userRepository.findById(followEntity.getFollowerId());

            if (userEntity.isPresent()) {
                ProfileInformation profileInformation = new ProfileInformation(userEntity.get().getId(), userEntity.get().getFirstName(), userEntity.get().getLastName(),
                        userEntity.get().getUserName(), imageService.downloadImage(userEntity.get().getId()), getFollowersNumber(userId), getFollowingNumber(userId));
                profileInformations.add(profileInformation);
            }
        }
        return profileInformations;
    }

    @Override
    public Long getFollowersNumber(Long userId){
        return (long) followRepository.findAllByFollowingId(userId).size();
    }

    @Override
    public Long getFollowingNumber(Long userId){
        return (long) followRepository.findAllByFollowerId(userId).size();
    }

    @Override
    public List<ProfileInformation> getFollowing(Long userId) {
        List<FollowEntity> followEntities = followRepository.findAllByFollowerId(userId);

        List<ProfileInformation> profileInformations = new ArrayList<>();

        for (FollowEntity followEntity : followEntities) {
            Optional<UserEntity> userEntity = userRepository.findById(followEntity.getFollowingId());

            if (userEntity.isPresent()) {
                ProfileInformation profileInformation = new ProfileInformation(userEntity.get().getId(), userEntity.get().getFirstName(), userEntity.get().getLastName(),
                        userEntity.get().getUserName(), imageService.downloadImage(userEntity.get().getId()), getFollowersNumber(userId), getFollowingNumber(userId));
                profileInformations.add(profileInformation);
            }
        }
        return profileInformations;
    }




}
