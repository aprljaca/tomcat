package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;

import java.time.OffsetDateTime;
import java.util.List;

public interface PostService {

    CreatePostResponse createPost(User user, CreatePostRequest request) throws BadRequestException;

    void setLike(User user, Post post);

    void commentPost(CommentRequest request, UserEntity userEntity) throws BadRequestException;

    Boolean isLiked(User user, Long postId);

    void setDislike(User user, Post post) throws BadRequestException;

    List<Post> getFollowingPosts(User user) throws UserNotFoundException;

    List<Post> getProfilePosts(User user, Long userId) throws UserNotFoundException;

    String calculateDuration(OffsetDateTime time);
}
