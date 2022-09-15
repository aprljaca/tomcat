package com.tomcat.service;

import com.tomcat.model.RegisterRequest;
import com.tomcat.model.RegisterResponse;

import java.util.List;

public interface UserService {
    public List<RegisterResponse> getAllUsers();

    void registerUser(RegisterRequest request);
}
