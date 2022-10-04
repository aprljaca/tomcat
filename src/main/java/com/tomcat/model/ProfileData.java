package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class ProfileData {
    private String firstName;
    private String lastName;
    private String userName;
    private HashMap<Long, PostData> posts;
}
