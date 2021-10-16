package com.metalservices.mvc.entity;

import org.springframework.dao.EmptyResultDataAccessException;

public enum OSErrorCodes {
    OS_ERROR_INVALID_ARGUMENTS(0),
    OS_UNEXPECTED_ERROR(1),
    OS_EMPTY_RESULT_DATA_ACCESS(2),
    OS_ENTITY_DOES_NOT_EXIST(3);

    private int value;

    OSErrorCodes(int code) {
        this.value = code;
    }
}
