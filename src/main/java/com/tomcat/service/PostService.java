package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;

import java.time.OffsetDateTime;
import java.util.List;

public interface PostService {

    CreatePostResponse createPost(User user, CreatePostRequest request) throws BadRequestException;

    void setLike(User user, Post post) throws UserNotFoundException;

    void commentPost(User user, CommentRequest request) throws BadRequestException, UserNotFoundException;

    Boolean isLiked(User user, Long postId);

    void setDislike(User user, Post post) throws BadRequestException;

    List<Post> getFollowingPosts(User user) throws UserNotFoundException, BadRequestException;

    List<Post> getProfilePosts(User user, Long userId) throws UserNotFoundException, BadRequestException;

    String calculateDuration(OffsetDateTime time);

    Boolean deletePost(User user, Post post) throws BadRequestException;
}
