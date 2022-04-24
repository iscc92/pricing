package com.calcoa.pricing.exceptions;

import java.util.function.Supplier;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    public CustomerNotFoundException(String errorMessage) {
    }
}
