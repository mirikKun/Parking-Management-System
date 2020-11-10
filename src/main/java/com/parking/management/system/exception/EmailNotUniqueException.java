package com.parking.management.system.exception;

public class EmailNotUniqueException extends RuntimeException{

    public EmailNotUniqueException(String errorMessage) {
        super(errorMessage);
    }
}
