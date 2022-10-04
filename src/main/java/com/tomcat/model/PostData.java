package com.tomcat.model;

import java.time.OffsetDateTime;

public class PostData {
    private Long postId;
    private Long userId;
    private String text;
    private OffsetDateTime createdTime;
    private int likesNumber;
    private int commentsNumber;

}
