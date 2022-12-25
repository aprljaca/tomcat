package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentInformation {
    private Long postId;
    private String firstName;
    private String lastName;
    private String text;
    private String profileImage;
    private String createdTime;
}
