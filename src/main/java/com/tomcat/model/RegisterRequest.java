package com.tomcat.model;

import com.tomcat.validation.CustomPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
@Data
@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String userName;
    @Email(message = "Email is not valid",
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @CustomPassword
    private String password;
}
