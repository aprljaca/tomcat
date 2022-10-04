package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.CreatePostRequest;
import com.tomcat.model.CreatePostResponse;

public interface PostService {

    CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request);
}
