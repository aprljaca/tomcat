package com.tomcat.service;

import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;

public interface ProfileService {

    ProfileInformation getProfileInformation(Long userId) throws UserNotFoundException;
}
