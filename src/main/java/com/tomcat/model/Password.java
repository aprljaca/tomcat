package com.tomcat.model;

import com.tomcat.validation.CustomPassword;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class Password {
    private String oldPassword;
    @CustomPassword
    private String newPassword;
}
