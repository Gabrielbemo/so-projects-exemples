package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.out.FieldErrorDTO;
import com.metalservices.mvc.controllers.dtos.out.SOErrorDTO;
import com.metalservices.mvc.entity.OSErrorCodes;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SOErrorDTO handlerMethodArgumentNotValidException2(final MethodArgumentNotValidException ex){


        List<FieldErrorDTO> fieldErrorDTO = ex
                .getAllErrors()
                .stream()
                .map(error -> {
                    return (FieldError)error;
                })
                .map(FieldErrorDTO::fromFieldError)
                .collect(Collectors.toList());

        SOErrorDTO soErrorDTO = SOErrorDTO.from(BAD_REQUEST, OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS,"Invalid Arguments", fieldErrorDTO);

        return soErrorDTO;
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(NOT_FOUND)
    public SOErrorDTO handlerEmptyResultDataAccessException(final EmptyResultDataAccessException ex){

        SOErrorDTO soErrorDTO = SOErrorDTO.from(NOT_FOUND, OSErrorCodes.OS_EMPTY_RESULT_DATA_ACCESS, ex.getLocalizedMessage(), null);

        return soErrorDTO;
    }
}
