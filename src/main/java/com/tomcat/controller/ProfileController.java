package com.tomcat.controller;

import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Post;
import com.tomcat.model.ProfileInformation;
import com.tomcat.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profilePostData")
    public List<Post> getProfilePosts(@RequestParam("userId") Long userId) throws UserNotFoundException {
        return profileService.getProfilePosts(userId);
    }

    @GetMapping("/profileInformationData")
    public ProfileInformation getProfileInformation(@RequestParam("userId") Long userId) throws UserNotFoundException {
        return profileService.getProfileInformation(userId);
    }



}
