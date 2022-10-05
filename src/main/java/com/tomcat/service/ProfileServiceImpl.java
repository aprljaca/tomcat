package com.tomcat.service;

import com.tomcat.entity.CommentEntity;
import com.tomcat.entity.LikeEntity;
import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.Post;
import com.tomcat.model.ProfileInformation;
import com.tomcat.repository.CommentRepository;
import com.tomcat.repository.LikeRepository;
import com.tomcat.repository.PostRepository;
import com.tomcat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Post> getProfilePosts(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        List<PostEntity> postEntitie = postRepository.findAllByUserId(userId);
        List<Post> postList = new ArrayList<>();

        for (PostEntity postEntity : postEntitie) {
            List<LikeEntity> likeEntities = likeRepository.findAllByPostId(postEntity.getId());
            List<CommentEntity> commentEntities = commentRepository.findAllByPostId(postEntity.getId());

            Post post = new Post(postEntity.getId(), postEntity.getUserId(), postEntity.getText(),
                    postEntity.getCreatedTime(), likeEntities.size(), commentEntities.size());

            postList.add(post);
        }
        return postList;
    }

    @Override
    public ProfileInformation getProfileInformation(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        return new ProfileInformation(userEntity.get().getFirstName(), userEntity.get().getLastName(), userEntity.get().getUserName());
    }
}
