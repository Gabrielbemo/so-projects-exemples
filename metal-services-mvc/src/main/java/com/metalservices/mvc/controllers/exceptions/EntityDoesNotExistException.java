package com.metalservices.mvc.controllers.exceptions;

public class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
