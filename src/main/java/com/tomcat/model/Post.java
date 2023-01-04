package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class Post {
    private Long postId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String text;
    private String createdTime;
    private int likesNumber;
    private int commentsNumber;
    private String profileImage;
    private List<CommentInformation> commentInfoList;
    private Boolean isLiked;
    private Boolean myPost;
}
