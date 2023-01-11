package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class NotificationInformation {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String profileImage;
    private Long postId;
}
