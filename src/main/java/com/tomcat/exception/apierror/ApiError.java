package com.tomcat.exception.apierror;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubErrorContainer> subErrors;

    private void addSubError(ApiSubErrorContainer subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    public void buildValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    public void buildValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    // ObjectError encapsulates an object error, a global reason for rejecting an object.
    private void addObjectValidationError(ObjectError objectError) {
        this.buildValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage()
        );
    }
    public void addObjectValidationErrors(List<ObjectError> globalErrors) {
        if(globalErrors.isEmpty()) {
            return;
        }

        globalErrors.forEach(this::addObjectValidationError);
    }

    // FieldError encapsulates a field error, a reason for rejecting a specific field value.
    private void addFieldValidationError(FieldError fieldError) {
        this.buildValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }
    public void addFieldValidationErrors(List<FieldError> fieldErrors) {
        if (fieldErrors.isEmpty()) {
            return;
        }

        fieldErrors.forEach(this::addFieldValidationError);
    }

    private void addConstraintValidationError(ConstraintViolation<?> cv) {
        this.buildValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }
    public void addConstraintValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addConstraintValidationError);
    }
}