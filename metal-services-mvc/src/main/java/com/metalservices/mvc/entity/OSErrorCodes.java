package com.metalservices.mvc.entity;

public enum OSErrorCodes {
    OS_ERROR_INVALID_ARGUMENTS(0),
    OS_UNEXPECTED_ERROR(1);

    private int value;

    OSErrorCodes(int code) {
        this.value = code;
    }
}
