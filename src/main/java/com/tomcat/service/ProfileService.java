package com.tomcat.service;

import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Post;
import com.tomcat.model.ProfileInformation;

import java.util.List;

public interface ProfileService {
    List<Post> getProfilePosts(Long userId) throws UserNotFoundException;

    ProfileInformation getProfileInformation(Long userId) throws UserNotFoundException;
}
