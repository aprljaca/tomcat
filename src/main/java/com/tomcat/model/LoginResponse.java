package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
@AllArgsConstructor
public class LoginResponse  {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

}