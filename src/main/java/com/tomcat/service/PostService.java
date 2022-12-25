package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.CommentRequest;
import com.tomcat.model.CreatePostRequest;
import com.tomcat.model.CreatePostResponse;
import com.tomcat.model.Post;

import java.time.OffsetDateTime;
import java.util.List;

public interface PostService {

    CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request) throws BadRequestException;

    void setLike(UserEntity userEntity, Post post);

    void commentPost(CommentRequest request, UserEntity userEntity) throws BadRequestException;

    Boolean isLiked(UserEntity userEntity, Long postId);

    void setDislike(UserEntity userEntity, Post post) throws BadRequestException;

    List<Post> getFollowingPosts(UserEntity userEntity) throws UserNotFoundException;

    List<Post> getProfilePosts(UserEntity userEntity, Long userId) throws UserNotFoundException;

    String calculateDuration(OffsetDateTime time);
}
