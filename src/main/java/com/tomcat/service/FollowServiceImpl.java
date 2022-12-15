package com.tomcat.service;

import com.tomcat.entity.FollowEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

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
}
