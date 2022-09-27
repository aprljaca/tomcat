package com.tomcat.application;

import com.tomcat.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private PasswordService passwordService;

    @Override
    public void run(String... args) throws Exception {
        passwordService.deleteExpiredTokens();
    }
}