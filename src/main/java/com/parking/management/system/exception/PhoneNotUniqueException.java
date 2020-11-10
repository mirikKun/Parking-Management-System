package com.parking.management.system.exception;

public class PhoneNotUniqueException extends RuntimeException{

    public PhoneNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
