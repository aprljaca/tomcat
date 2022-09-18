package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}