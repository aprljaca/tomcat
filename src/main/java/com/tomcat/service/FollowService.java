package com.tomcat.service;

import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;
import com.tomcat.model.User;

import java.util.List;

public interface FollowService {
    void followUser(User user, Long userId) throws BadRequestException, UserNotFoundException;

    void unFollowUser(User user, Long userId) throws BadRequestException, UserNotFoundException;

    Boolean isFollowing(User user, Long userId) throws UserNotFoundException;

    List<ProfileInformation> getFollowing(Long userId);

    List<ProfileInformation> getFollowers(Long userId);

    Long getFollowersNumber(Long userId);

    Long getFollowingNumber(Long userId);
}
