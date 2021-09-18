package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.out.FieldErrorDTO;
import com.metalservices.mvc.controllers.dtos.out.SOErrorDTO;
import com.metalservices.mvc.entity.OSErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SOErrorDTO handlerMethodArgumentNotValidException(final ConstraintViolationException ex){
        List<FieldErrorDTO> fieldErrorDTO = ex
                .getConstraintViolations()
                .stream()
                .map(FieldErrorDTO::from)
                .collect(Collectors.toList());

        SOErrorDTO soErrorDTO = SOErrorDTO.from(BAD_REQUEST, OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS,"Invalid Arguments", fieldErrorDTO);

        return soErrorDTO;
    }
}
