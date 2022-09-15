package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;
import com.tomcat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<RegisterResponse> getAllUsers() {
        List<RegisterResponse> list = new ArrayList<>();
        list.add(new RegisterResponse("Admir", "Prljaca", "062225938", "test123"));
        return list;
    }

    @Override
    public void registerUser(RegisterRequest request) {
        UserEntity userEntity = new UserEntity(request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getEmail(), request.getPassword());
        userRepository.save(userEntity);
    }
}
