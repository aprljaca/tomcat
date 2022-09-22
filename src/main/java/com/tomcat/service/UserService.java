package com.tomcat.service;

import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.model.LoginRequest;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException;

    ResponseEntity<?> loginUser(LoginRequest request) throws BadCredentialsException;
}

