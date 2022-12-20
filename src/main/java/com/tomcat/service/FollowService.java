package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.model.ProfileInformation;

import java.util.List;

public interface FollowService {
    void followUser(UserEntity userEntity, Long userId) throws BadRequestException;

    void unFollowUser(UserEntity userEntity, Long userId) throws BadRequestException;

    Boolean isFollowing(UserEntity userEntity, Long userId);

    List<ProfileInformation> getFollowing(Long userId);
}
