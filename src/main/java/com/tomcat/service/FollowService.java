package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;

public interface FollowService {
    void followUser(UserEntity userEntity, Long userId) throws BadRequestException;

    void unFollowUser(UserEntity userEntity, Long userId) throws BadRequestException;

    Boolean isFollowing(UserEntity userEntity, Long userId);


}
