package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import com.tomcat.service.PostService;
import com.tomcat.service.ProfileService;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class PostController {

    private final PostService postService;

    private final Mapper mapper;

    @PostMapping("/createPost")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request, @AuthenticationPrincipal UserEntity userEntity) throws BadRequestException {
        CreatePostResponse user = postService.createPost(mapper.mapUserEntityToUserDto(userEntity), request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@AuthenticationPrincipal UserEntity userEntity, @RequestBody Post post) {
        postService.setLike(mapper.mapUserEntityToUserDto(userEntity), post);
        return new ResponseEntity<>("Post successfully liked", HttpStatus.CREATED);
    }

    @DeleteMapping("/dislike")
    public ResponseEntity<?> dislike(@AuthenticationPrincipal UserEntity userEntity, @RequestBody Post post) throws BadRequestException {
        postService.setDislike(mapper.mapUserEntityToUserDto(userEntity), post);
        return new ResponseEntity<>("Post successfully disliked", HttpStatus.OK);
    }

    @PostMapping("/commentPost")
    public ResponseEntity<?> commentPost(@RequestBody CommentRequest request, @AuthenticationPrincipal UserEntity userEntity) throws BadRequestException {
        postService.commentPost(request, userEntity);
        return new ResponseEntity<>("Post successfully commented", HttpStatus.CREATED);
    }

    @GetMapping("/isLiked")
    public ResponseEntity<Boolean> isLiked(@AuthenticationPrincipal UserEntity userEntity, @RequestBody Post post) {
        Boolean liked = postService.isLiked(mapper.mapUserEntityToUserDto(userEntity), post.getPostId());
        return new ResponseEntity<>(liked, HttpStatus.OK);
    }

    @GetMapping("/followingPosts")
    public List<Post> getFollowingPosts(@AuthenticationPrincipal UserEntity userEntity) throws UserNotFoundException {
        return postService.getFollowingPosts(mapper.mapUserEntityToUserDto(userEntity));
    }

    @GetMapping("/profilePosts")
    public List<Post> getProfilePosts(@AuthenticationPrincipal UserEntity userEntity, @RequestParam("userId") Long userId) throws UserNotFoundException {
        return postService.getProfilePosts(mapper.mapUserEntityToUserDto(userEntity), userId);
    }
}
