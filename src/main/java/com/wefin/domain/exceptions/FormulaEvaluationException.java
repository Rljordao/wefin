package com.wefin.domain.exceptions;

import org.springframework.http.HttpStatus;

public class FormulaEvaluationException  extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "FORMULA_VALIDATION_ERROR";

    public FormulaEvaluationException(String message) {
        super(message, DEFAULT_ERROR_CODE, HttpStatus.BAD_REQUEST.value());
    }

    public FormulaEvaluationException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value());
    }
}
