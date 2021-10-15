package com.metalservices.mvc.controllers.dtos.out;

import lombok.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorDTO {
    private String field;
    private String message;

    public static FieldErrorDTO from(final ConstraintViolation fieldError) {
        return FieldErrorDTO.builder()
                .field(fieldError.getPropertyPath().toString())
                .message(fieldError.getMessageTemplate())
                .build();
    }

    public static FieldErrorDTO fromFieldError(final FieldError fieldError) {
        return FieldErrorDTO.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
