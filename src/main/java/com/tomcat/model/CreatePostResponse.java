package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class CreatePostResponse {
    private Long userId;
    private String text;
    private OffsetDateTime createdTime;
}
