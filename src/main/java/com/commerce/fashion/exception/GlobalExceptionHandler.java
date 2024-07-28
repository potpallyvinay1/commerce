package com.commerce.fashion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateShippingAddressException.class)
    public ResponseEntity<ApiError> handleDuplicateShippingAddressException(DuplicateShippingAddressException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicateBillingAddressException.class)
    public ResponseEntity<ApiError> handleDuplicateBillingAddressException(DuplicateBillingAddressException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomEntityNotFoundExceptionn(CustomEntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistingShippingAddressNotFoundException.class)
    public ResponseEntity<ApiError> handleExistingShippingAddressNotFoundException(ExistingShippingAddressNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistingBillingAddressNotFoundException.class)
    public ResponseEntity<ApiError> handleExistingBillingAddressNotFoundException(ExistingBillingAddressNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
