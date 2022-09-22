package com.tomcat.util;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.LoginResponse;
import com.tomcat.model.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public RegisterResponse mapUserEntityToRegisterDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();

        return new RegisterResponse(firstName, lastName, userName, email);
    }

    public LoginResponse mapUserToLoginDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUsername();
        String email = user.getEmail();

        return new LoginResponse(firstName, lastName, userName, email);
    }

}
