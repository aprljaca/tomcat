package com.tomcat.model;

import com.tomcat.validation.CustomPassword;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class Password {
    @Email(message = "Email is not valid",
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private String oldPassword;
    @CustomPassword
    private String newPassword;
}
