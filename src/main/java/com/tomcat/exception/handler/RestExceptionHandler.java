package com.tomcat.exception.handler;

import com.tomcat.exception.ExpiredTokenException;
import com.tomcat.exception.InvalidOldPasswordException;
import com.tomcat.exception.InvalidTokenException;
import com.tomcat.exception.UserNotFoundException;
import com.tomcat.exception.apierror.ApiError;
//import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";

        ApiError newApiError = ApiError.builder().status(BAD_REQUEST).timestamp(LocalDateTime.now()).message(error).debugMessage(ex.getLocalizedMessage()).build();

        return buildResponseEntity(newApiError);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).timestamp(LocalDateTime.now()).message("Validation error").debugMessage(ex.getLocalizedMessage()).build();

        apiError.addFieldValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addObjectValidationErrors(ex.getBindingResult().getGlobalErrors());

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        ApiError apiError = ApiError.builder().status(UNAUTHORIZED).timestamp(LocalDateTime.now()).message(ex.getMessage()).build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    protected ResponseEntity<Object> handleUserExists(ExpiredTokenException ex) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).timestamp(LocalDateTime.now()).message(ex.getMessage()).build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    protected ResponseEntity<Object> handleUserExists(InvalidOldPasswordException ex) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).timestamp(LocalDateTime.now()).message(ex.getMessage()).build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleUserExists(InvalidTokenException ex) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).timestamp(LocalDateTime.now()).message(ex.getMessage()).build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserExists(UserNotFoundException ex) {
        ApiError apiError = ApiError.builder().status(NOT_FOUND).timestamp(LocalDateTime.now()).message(ex.getMessage()).build();

        return buildResponseEntity(apiError);
    }
}