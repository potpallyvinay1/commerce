package com.commerce.fashion.exception;

public class DuplicateBillingAddressException extends RuntimeException{
    public DuplicateBillingAddressException(String message) {
        super(message);
    }
}
