package com.tomcat.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}