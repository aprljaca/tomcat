package com.tomcat.service;

import com.tomcat.entity.ProfileImageEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;
import com.tomcat.repository.*;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private  ImageService imageService;
    @Autowired
    private FollowService followService;
    private final Mapper mapper;

    @Override
    public ProfileInformation getProfileInformation(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<ProfileImageEntity> imageEntity = imageRepository.findByUserId(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }

        return new ProfileInformation(userId, userEntity.get().getFirstName(), userEntity.get().getLastName(),
                userEntity.get().getUserName(), imageService.downloadImage(userId), followService.getFollowersNumber(userId), followService.getFollowingNumber(userId));
    }

    @Override
    public List<ProfileInformation> getRandomProfiles(Long userId) {
        List<UserEntity> userEntities = userRepository.findRandomProfiles(userId);
        List<ProfileInformation> randomProfilesList = new ArrayList<>();

        for(UserEntity userEntity: userEntities){
            String profileImage = imageService.downloadImage(userEntity.getId());
            randomProfilesList.add(mapper.mapUserEntityToProfileInformation(userEntity, profileImage, followService.getFollowersNumber(userId), followService.getFollowingNumber(userId)));
        }
        return randomProfilesList;
    }
}
