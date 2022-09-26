package com.tomcat.exception;

public class InvalidEmailFormatException extends Exception {
    public InvalidEmailFormatException(String errorMessage) {
        super(errorMessage);
    }
}