package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.CreatePostRequest;
import com.tomcat.model.CreatePostResponse;
import com.tomcat.model.SetLikeRequest;
import com.tomcat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request, @AuthenticationPrincipal UserEntity userEntity) {
        CreatePostResponse user = postService.createPost(userEntity, request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/setLike")
    public ResponseEntity<?> setLike(@RequestBody SetLikeRequest request){
        postService.setLike(request);
        return new ResponseEntity<>("Liked", HttpStatus.CREATED);
    }

}
