package com.tomcat.service;

import com.tomcat.entity.FollowEntity;
import com.tomcat.entity.ProfileImageEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.model.ProfileInformation;
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
    public void followUser(UserEntity userEntity, Long userId) throws BadRequestException {
        FollowEntity followEntity = new FollowEntity(userEntity.getId(), userId);
        if(isFollowing(userEntity, userId)){
            throw new BadRequestException("Bad request!");
        }
        followRepository.save(followEntity);
    }

    @Override
    public void unFollowUser(UserEntity userEntity, Long userId) throws BadRequestException {
        Optional<FollowEntity> followEntity = followRepository.findFollower(userEntity.getId(), userId);
        if(followEntity.isEmpty()){
            throw new BadRequestException("Bad request!");
        }
        followRepository.delete(followEntity.get());
    }

    @Override
    public Boolean isFollowing(UserEntity userEntity, Long userId) {
        Optional<FollowEntity> followEntity = followRepository.findFollower(userEntity.getId(), userId);
        return followEntity.isPresent();
    }

    @Override
    public List<ProfileInformation> getFollowing(Long userId) {
        List<FollowEntity> followEntities = followRepository.findAllByFollowerId(userId);

        List<ProfileInformation> profileInformations = new ArrayList<>();

        for(FollowEntity followEntity : followEntities){
            Optional<UserEntity> userEntity = userRepository.findById(followEntity.getFollowingId());

            if(userEntity.isPresent()){
                ProfileInformation profileInformation = new ProfileInformation(userEntity.get().getId(), userEntity.get().getFirstName(), userEntity.get().getLastName(), userEntity.get().getUserName(), imageService.downloadImage(userEntity.get().getId()));
                profileInformations.add(profileInformation);
            }
        }
        return profileInformations;
    }
}
