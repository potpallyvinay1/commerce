package com.commerce.fashion.exception;

public class ExistingShippingAddressNotFoundException extends RuntimeException{
    public ExistingShippingAddressNotFoundException(String message) {
        super(message);
    }
}
