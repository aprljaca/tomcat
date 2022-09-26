package com.tomcat.exception;

public class ExpiredTokenException extends Exception {
    public ExpiredTokenException(String errorMessage) {
        super(errorMessage);
    }
}