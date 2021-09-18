package com.metalservices.mvc.controllers.dtos.out;

import lombok.*;

import javax.validation.ConstraintViolation;

@Data
@Builder
public class FieldErrorDTO {
    private String field;
    private String message;

    public static FieldErrorDTO from(ConstraintViolation fieldError) {
        return FieldErrorDTO.builder()
                .field(fieldError.getPropertyPath().toString())
                .message(fieldError.getMessageTemplate())
                .build();
    }
}
