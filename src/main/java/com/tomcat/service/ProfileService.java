package com.tomcat.service;

import com.tomcat.model.ProfileData;

public interface ProfileService {
    ProfileData getProfileData(Long userId);
}
