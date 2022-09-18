package com.tomcat.util;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public RegisterResponse mapUserToRegisterDto(UserEntity user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();

        return new RegisterResponse(firstName, lastName, userName, email);
    }
}
