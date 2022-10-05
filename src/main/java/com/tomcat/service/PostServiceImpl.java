package com.tomcat.service;

import com.tomcat.entity.LikeEntity;
import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.model.CreatePostRequest;
import com.tomcat.model.CreatePostResponse;
import com.tomcat.model.SetLikeRequest;
import com.tomcat.repository.LikeRepository;
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

    private final LikeRepository likeRepository;

    @Override
    public CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request) {
        PostEntity post = new PostEntity(request.getText(), OffsetDateTime.now(), userEntity.getId());
        postRepository.save(post);
        return mapper.mapPostEntityToPostDto(post);
    }

    @Override
    public void setLike(SetLikeRequest request) {
        LikeEntity like = new LikeEntity(request.getPostId(), request.getUserId(), OffsetDateTime.now());
        likeRepository.save(like);
    }


}
