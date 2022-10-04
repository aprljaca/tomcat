package com.tomcat.controller;

import com.tomcat.model.ProfileData;
import com.tomcat.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/profileData")
    public ProfileData test(@RequestParam("userId") Long userId){
        ProfileData profileData = profileService.getProfileData(userId);
        return null;
    }
}
