package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.exception.UserAlreadyExistsException;
import com.tomcat.model.LoginRequest;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.CustomPasswordEncoder;
import com.tomcat.util.JwtUtil;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final Mapper mapper;


    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }

        if(userRepository.findByUserName(request.getUserName()).isPresent()){
            throw new UserAlreadyExistsException("This email is already in use");
        }
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), request.getPassword());
        userRepository.save(userEntity);

        return mapper.mapUserEntityToRegisterDto(userEntity);
    }

    @Override
    public ResponseEntity<?>  loginUser(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUserName(), request.getPassword()
                            )
                    );

            UserEntity user = (UserEntity) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(user)
                    )
                    .body(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
