package com.tomcat.controller;

import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.model.LoginRequest;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import com.tomcat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        RegisterResponse user = userService.registerUser(request);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        return userService.loginUser(request);
    }

}
