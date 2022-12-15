package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/followUser")
    public ResponseEntity<?> follow(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws BadRequestException {
        System.out.println("doslo");
        followService.followUser(userEntity, userId);
        return new ResponseEntity<>("Following user", HttpStatus.OK);
    }

    @DeleteMapping("/unFollowUser")
    public ResponseEntity<?> unFollow(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws BadRequestException {
        followService.unFollowUser(userEntity, userId);
        return new ResponseEntity<>("User unfollowed", HttpStatus.OK);
    }

    @GetMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) {
        Boolean following = followService.isFollowing(userEntity, userId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }
}
