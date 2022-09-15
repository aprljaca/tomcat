package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse  {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
