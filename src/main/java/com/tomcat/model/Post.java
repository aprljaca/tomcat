package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Getter
@AllArgsConstructor
public class Post {
    private Long postId;
    private Long userId;
    private String text;
    private OffsetDateTime createdTime;
    private int likesNumber;
    private int commentsNumber;
}
