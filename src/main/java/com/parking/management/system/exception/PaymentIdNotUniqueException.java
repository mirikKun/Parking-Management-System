package com.parking.management.system.exception;

public class PaymentIdNotUniqueException extends RuntimeException {

    public PaymentIdNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
