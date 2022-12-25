package com.tomcat.service;

import com.tomcat.entity.*;
import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import com.tomcat.repository.CommentRepository;
import com.tomcat.repository.LikeRepository;
import com.tomcat.repository.PostRepository;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Mapper mapper;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    @Override
    public CreatePostResponse createPost(UserEntity userEntity, CreatePostRequest request) throws BadRequestException {
        if (request.getText().isEmpty()) {
            throw new BadRequestException("Post text can not be empty!");
        }
        PostEntity post = new PostEntity(request.getText(), OffsetDateTime.now(), userEntity.getId());
        postRepository.save(post);
        return mapper.mapPostEntityToPostDto(post);
    }

    @Override
    public void setLike(UserEntity userEntity, Post post) {
        if (!isLiked(userEntity, post.getPostId())) {
            LikeEntity like = new LikeEntity(post.getPostId(), userEntity.getId(), OffsetDateTime.now());
            likeRepository.save(like);
        }
    }

    @Override
    public void commentPost(CommentRequest request, UserEntity userEntity) throws BadRequestException {
        if (request.getText().isEmpty()) {
            throw new BadRequestException("Comment can not be empty!");
        }
        CommentEntity comment = new CommentEntity(request.getPostId(), userEntity.getId(), request.getText(), OffsetDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public Boolean isLiked(UserEntity userEntity, Long postId) {
        Optional<LikeEntity> likeEntity = likeRepository.findLike(userEntity.getId(), postId);
        return likeEntity.isPresent();
    }

    @Override
    public void setDislike(UserEntity userEntity, Post post) throws BadRequestException {
        if (!isLiked(userEntity, post.getPostId())) {
            throw new BadRequestException("Bad request!");
        }
        Optional<LikeEntity> likeEntity = likeRepository.findLike(userEntity.getId(), post.getPostId());
        likeRepository.delete(likeEntity.get());
    }

    @Override
    public List<Post> getFollowingPosts(UserEntity userEntity) throws UserNotFoundException {
        return null;
    }

    @Override
    public List<Post> getProfilePosts(UserEntity loggedUser, Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by id!");
        }
        List<PostEntity> postEntitie = postRepository.findAllByUserId(userId);
        List<Post> postList = new ArrayList<>();

        for (PostEntity postEntity : postEntitie) {
            List<LikeEntity> likeEntityList = likeRepository.findAllByPostId(postEntity.getId());
            List<CommentEntity> commentEntityList = commentRepository.findAllByPostId(postEntity.getId());

            List<CommentInformation> commentInformationList = new ArrayList<>();
            for (CommentEntity commentEntity : commentEntityList) {
                Optional<UserEntity> commentBy = userRepository.findById(commentEntity.getUserId());
                commentInformationList
                        .add(mapper.mapCommentEntityToCommentInformationDto(commentEntity, commentBy.get().getFirstName(), commentBy.get().getLastName(),
                                imageService.downloadImage(commentEntity.getUserId()), calculateDuration(commentEntity.getCreatedTime())));
            }

            Post post = new Post(postEntity.getId(), postEntity.getUserId(), userEntity.get().getFirstName(), userEntity.get().getLastName(), postEntity.getText(),
                    calculateDuration(postEntity.getCreatedTime()), likeEntityList.size(), commentEntityList.size(), imageService.downloadImage(userId), commentInformationList,
                    isLiked(loggedUser, postEntity.getId()));

            postList.add(post);
        }
        return postList;
    }

    @Override
    public String calculateDuration(OffsetDateTime time) {
        OffsetDateTime now = OffsetDateTime.now();

        if (ChronoUnit.SECONDS.between(time, now) < 60) {
            return "a few moments ago";
        } else if (ChronoUnit.MINUTES.between(time, now) < 60) {
            return ChronoUnit.MINUTES.between(time, now) + " minutes ago";
        } else if (ChronoUnit.HOURS.between(time, now) < 24) {
            return ChronoUnit.HOURS.between(time, now) + " hours ago";
        } else if (ChronoUnit.DAYS.between(time, now) < 7) {
            return ChronoUnit.DAYS.between(time, now) + " days ago";
        }
        return ChronoUnit.WEEKS.between(time, now) + " weeks ago";
    }
}
