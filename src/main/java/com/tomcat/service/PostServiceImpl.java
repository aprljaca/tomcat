package com.tomcat.service;

import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.model.CreatePostRequest;
import com.tomcat.model.CreatePostResponse;
import com.tomcat.repository.PostRepository;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Mapper mapper;

    private final PostRepository postRepository;

    @Override
    public CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request) {
        PostEntity post = new PostEntity(request.getText(), OffsetDateTime.now(), userEntity.getId());
        postRepository.save(post);
        return mapper.mapPostEntityToPostDto(post);
    }
}
