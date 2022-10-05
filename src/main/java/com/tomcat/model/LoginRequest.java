package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
