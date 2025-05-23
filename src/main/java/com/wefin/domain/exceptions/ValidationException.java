package com.wefin.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "VALIDATION_ERROR";

    public ValidationException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST.value());
    }

    public ValidationException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value());
    }
}

