package com.tomcat.service;

import com.tomcat.exception.BadRequestException;
import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException;

    ResponseEntity<?> loginUser(LoginRequest request) throws BadCredentialsException;

    User findUserByEmail(String email) throws UserNotFoundException;

    List<ProfileInformation> getSearchedUser(String inputValue) throws BadRequestException;
}

