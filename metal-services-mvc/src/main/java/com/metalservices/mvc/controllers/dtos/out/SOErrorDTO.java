package com.metalservices.mvc.controllers.dtos.out;

import com.metalservices.mvc.entity.OSErrorCodes;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class SOErrorDTO {
    private int code;
    private OSErrorCodes error;
    private String message;
    private List<FieldErrorDTO> fieldsErrors;

    public static SOErrorDTO from(final HttpStatus httpStatus, final OSErrorCodes osErrorCodes, final String message, final List<FieldErrorDTO> fieldErrorDTOS){
        return SOErrorDTO.builder()
                .code(httpStatus.value())
                .error(osErrorCodes)
                .message(message)
                .fieldsErrors(fieldErrorDTOS)
                .build();
    }
}
