package com.parking.management.system.exception;

public class UsernameNotUniqueException extends RuntimeException {

    public UsernameNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}

