package com.tomcat.util;

import com.tomcat.entity.CommentEntity;
import com.tomcat.entity.PostEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.model.*;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class Mapper {
    public RegisterResponse mapUserEntityToRegisterDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();

        return new RegisterResponse(firstName, lastName, userName, email);
    }

    public CreatePostResponse mapPostEntityToPostDto(PostEntity post) {
        Long userId = post.getUserId();
        String text = post.getText();
        OffsetDateTime createdTime = post.getCreatedTime();

        return new CreatePostResponse(userId, text, createdTime);
    }

    public LoginResponse mapUserToLoginDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUsername();
        String email = user.getEmail();

        return new LoginResponse(firstName, lastName, userName, email);
    }

    public User mapUserEntityToUserDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();

        return new User(firstName, lastName, userName, email, password);
    }

    public CommentInformation mapCommentEntityToCommentInformationDto(CommentEntity entity, String firstName, String lastName, String profilImage, String createdTime) {
        return new CommentInformation(entity.getPostId(), firstName, lastName, entity.getText(), profilImage, createdTime);
    }

}
