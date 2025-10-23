package com.carlosdev.DemandManager.exception;

public class UserIdNotExistException extends RuntimeException {

    public UserIdNotExistException(String message) {
        super(message);
    }
}
