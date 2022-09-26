package com.tomcat.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}