package com.commerce.fashion.exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

public class DuplicateShippingAddressException extends RuntimeException{
    public DuplicateShippingAddressException(String message) {
        super(message);
    }
}
