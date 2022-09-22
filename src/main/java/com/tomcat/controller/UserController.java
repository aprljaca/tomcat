package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.model.LoginRequest;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import com.tomcat.service.UserService;
import com.tomcat.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1")
public class UserController {

    private final UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        RegisterResponse user = userService.registerUser(request);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) throws BadCredentialsException {
        return userService.loginUser(request);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token, @AuthenticationPrincipal UserEntity userEntity){
        try{
            Boolean isTokenValid = jwtUtil.validateToken(token, userEntity);
            return ResponseEntity.ok(isTokenValid);
        } catch (ExpiredJwtException e){
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/create")
    public void create(@AuthenticationPrincipal UserEntity userEntity, @RequestBody LoginRequest request){
        System.out.println(userEntity.getUserName());
        System.out.println(request.getUserName());
    }

}
