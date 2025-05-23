package com.wefin.domain.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "BUSINESS_ERROR";

    public BusinessException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST.value());
    }

    public BusinessException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value());
    }

    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value(), cause);
    }
}