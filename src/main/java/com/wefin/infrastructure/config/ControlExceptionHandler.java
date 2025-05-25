package com.wefin.infrastructure.config;


import com.wefin.application.util.DateResolver;
import com.wefin.domain.exceptions.BaseException;
import com.wefin.domain.exceptions.BusinessException;
import com.wefin.domain.exceptions.FormulaEvaluationException;
import com.wefin.domain.exceptions.ResourceNotFoundException;
import com.wefin.domain.exceptions.ValidationException;
import com.wefin.domain.entities.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(ex.getStatusCode())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormulaEvaluationException.class)
    public ResponseEntity<ApiErrorResponse> handleFormulaEvaluationException(FormulaEvaluationException ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ApiErrorResponse.ValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .toList();

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation error")
                .errorCode("VALIDATION_ERROR")
                .validationErrors(validationErrors)
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(String.format("The parameter '%s' should be of type '%s'",
                        ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()))
                .errorCode("TYPE_MISMATCH")
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllUncaughtException(Exception ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(DateResolver.localDateTimeNow())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred")
                .errorCode("INTERNAL_ERROR")
                .path(getRequestPath(request))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }
        return "";
    }
}