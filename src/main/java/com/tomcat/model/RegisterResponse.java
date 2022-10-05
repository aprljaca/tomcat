package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
@AllArgsConstructor
public class RegisterResponse  {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
