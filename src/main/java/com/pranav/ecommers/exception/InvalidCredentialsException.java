package com.pranav.ecommers.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
