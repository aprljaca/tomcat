package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.model.*;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.JwtUtil;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final Mapper mapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("This email is already in use");
        }
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);

        return mapper.mapUserEntityToRegisterDto(userEntity);
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) throws BadCredentialsException {
        System.out.println("tusam1");
        Optional<UserEntity> user = userRepository.findByUserName(request.getUserName());

        boolean isValidUser = (user.isPresent()) && (bCryptPasswordEncoder.matches(request.getPassword(), user.get().getPassword()));
        if (!isValidUser) {
            throw new BadCredentialsException("Bad credentials! Username and/or password incorrect!");

        }
        System.out.println("tusam2");
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtUtil.generateToken(user.get())
                )
                .body(mapper.mapUserToLoginDto(user.get()));
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("Can't find user by email!");
        }
        return mapper.mapUserEntityToUserDto(userEntity.get());
    }

}
