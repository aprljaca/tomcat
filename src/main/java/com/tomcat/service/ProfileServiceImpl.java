package com.tomcat.service;

import com.tomcat.model.ProfileData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    @Override
    public ProfileData getProfileData(Long userId) {
        return null;
    }




}





//profileData:
//    private String firstName;
//    private String lastName;
//    private String userName;
//    private HashMap<Long, PostData> posts;