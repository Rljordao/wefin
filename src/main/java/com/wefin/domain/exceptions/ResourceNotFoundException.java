package com.wefin.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";

    public ResourceNotFoundException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.NOT_FOUND.value());
    }

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(formatMessage(resourceName, identifier), DEFAULT_ERROR_CODE, HttpStatus.NOT_FOUND.value());
    }

    public ResourceNotFoundException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND.value());
    }

    private static String formatMessage(String resourceName, Object identifier) {
        return String.format("%s not found with identifier: %s", resourceName, identifier);
    }
}
