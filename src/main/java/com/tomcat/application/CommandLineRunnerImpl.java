package com.tomcat.application;

import com.tomcat.entity.LikeEntity;
import com.tomcat.repository.LikeRepository;
import com.tomcat.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private PasswordService passwordService;

    private final LikeRepository likeRepository;



    @Override
    public void run(String... args) throws Exception {
        passwordService.deleteExpiredTokens();
    }
}