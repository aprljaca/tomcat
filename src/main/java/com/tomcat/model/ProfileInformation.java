package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInformation {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String profileImage;
    private Long followersNumber;
    private Long followingNumber;
    
}
