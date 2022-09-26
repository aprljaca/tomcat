package com.tomcat.exception;

public class InvalidOldPasswordException extends Exception {
    public InvalidOldPasswordException(String errorMessage) {
        super(errorMessage);
    }
}