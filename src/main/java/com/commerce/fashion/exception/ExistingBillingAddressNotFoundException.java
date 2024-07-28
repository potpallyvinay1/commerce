package com.commerce.fashion.exception;

public class ExistingBillingAddressNotFoundException extends RuntimeException{
    public ExistingBillingAddressNotFoundException(String message) {
        super(message);
    }
}
