package com.tomcat.service;

import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;

import java.util.List;

public interface ProfileService {

    ProfileInformation getProfileInformation(Long userId) throws UserNotFoundException;

    List<ProfileInformation> getRandomProfiles(Long userId);
}
