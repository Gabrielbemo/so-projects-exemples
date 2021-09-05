package com.metalservices.mvc.controllers.dtos.out;

import com.metalservices.mvc.entity.ServiceOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateServiceOrderResponseDTOTest {
    LocalDateTime actualDate = LocalDateTime.now();

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void when_geId_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals(1L, createServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_getServiceOrderNumber_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals("123", createServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_getCreatedAt_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals(actualDate, createServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_setId_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(null,"123", actualDate);
        createServiceOrderResponseDTO.setId(1L);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals(1L, createServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_setServiceOrderNumber_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,null, actualDate);
        createServiceOrderResponseDTO.setServiceOrderNumber("123");

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals("123", createServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_setCreatedAt_withField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", null);
        createServiceOrderResponseDTO.setCreatedAt(actualDate);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals(actualDate, createServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_setId_withOutField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);
        createServiceOrderResponseDTO.setId(null);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, createServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_setServiceOrderNumber_withOutField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);
        createServiceOrderResponseDTO.setServiceOrderNumber(null);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, createServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_setCreatedAt_withOutField_thenDontReturnExeption() {
        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = new CreateServiceOrderResponseDTO(1L,"123", actualDate);
        createServiceOrderResponseDTO.setCreatedAt(null);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, createServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_fromEntityWithAllFields_then_ReturnAnDTO(){
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", actualDate);

        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = CreateServiceOrderResponseDTO.fromEntity(serviceOrder);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertEquals(createServiceOrderResponseDTO.getServiceOrderNumber(), "123");
        assertEquals(createServiceOrderResponseDTO.getCreatedAt(), actualDate);
        assertEquals(createServiceOrderResponseDTO.getId(), 1L);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_fromEntityWithNullFields_then_IsNotValidated(){
        ServiceOrder serviceOrder = new ServiceOrder(null, null, null);

        CreateServiceOrderResponseDTO createServiceOrderResponseDTO = CreateServiceOrderResponseDTO.fromEntity(serviceOrder);

        Set<ConstraintViolation<CreateServiceOrderResponseDTO>> violations = validator.validate(createServiceOrderResponseDTO);

        assertEquals(createServiceOrderResponseDTO.getServiceOrderNumber(), null);
        assertEquals(createServiceOrderResponseDTO.getCreatedAt(), null);
        assertEquals(createServiceOrderResponseDTO.getId(), null);
        assertFalse(violations.isEmpty());
    }
}
