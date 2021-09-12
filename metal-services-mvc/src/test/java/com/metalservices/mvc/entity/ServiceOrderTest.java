package com.metalservices.mvc.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceOrderTest {
    private LocalDateTime actualDate;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        actualDate = LocalDateTime.now();
    }

    @Test
    public void when_setIdAt_withField_thenDontReturnExeption(){
        ServiceOrder serviceOrder = new ServiceOrder(null, "123", actualDate);
        serviceOrder.setId(1L);

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_setId_withOutField_thenReturnExeption(){
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", actualDate);
        serviceOrder.setId(null);

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_setServiceOrderNumber_withField_thenDontReturnExeption() {
        ServiceOrder serviceOrder = new ServiceOrder(1L, null, actualDate);
        serviceOrder.setServiceOrderNumber("12345");

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertTrue(violations.isEmpty());
    }


    @Test
    public void when_setServiceOrderNumber_withOutField_thenReturnExeption() {
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", actualDate);
        serviceOrder.setServiceOrderNumber(null);

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_setCreateAt_withField_thenDontReturnExeption(){
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", null);
        serviceOrder.setCreatedAt(actualDate);

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_setCreateAt_withOutField_thenReturnExeption(){
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", actualDate);
        serviceOrder.setCreatedAt(null);

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertFalse(violations.isEmpty());
    }
}
