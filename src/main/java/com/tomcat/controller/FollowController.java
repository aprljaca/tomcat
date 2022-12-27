package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.ProfileInformation;
import com.tomcat.service.FollowService;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class FollowController {

    private final FollowService followService;

    private final Mapper mapper;

    @PostMapping("/followUser")
    public ResponseEntity<?> follow(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws BadRequestException, UserNotFoundException {
        followService.followUser(mapper.mapUserEntityToUserDto(userEntity), userId);
        return new ResponseEntity<>("Following user", HttpStatus.OK);
    }

    @DeleteMapping("/unFollowUser")
    public ResponseEntity<?> unFollow(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws BadRequestException, UserNotFoundException {
        followService.unFollowUser(mapper.mapUserEntityToUserDto(userEntity), userId);
        return new ResponseEntity<>("User unfollowed", HttpStatus.OK);
    }

    @GetMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws UserNotFoundException {
        Boolean following = followService.isFollowing(mapper.mapUserEntityToUserDto(userEntity), userId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    @GetMapping("/userFollowing")
    public List<ProfileInformation> userFollowing(@RequestParam("userId") Long userId) {
        return followService.getFollowing(userId);
    }

    @GetMapping("/userFollowers")
    public List<ProfileInformation> userFollowers(@RequestParam("userId") Long userId) {
        return followService.getFollowers(userId);
    }
}