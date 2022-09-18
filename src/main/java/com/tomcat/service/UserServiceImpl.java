package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import com.tomcat.repository.UserRepository;
import com.tomcat.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    @Override
    public void registerUser(RegisterRequest request) {
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), request.getPassword());
        userRepository.save(userEntity);
    }

    @Override
    public List<RegisterResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users
                .stream()
                .map(mapper::mapUserToRegisterDto)
                .toList();
    }


}
