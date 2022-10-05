package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.OffsetDateTime;
@Data
@Getter
@AllArgsConstructor
public class CreatePostResponse {
    private Long userId;
    private String text;
    private OffsetDateTime createdTime;
}
