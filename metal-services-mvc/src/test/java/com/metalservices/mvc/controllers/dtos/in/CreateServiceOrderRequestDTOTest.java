package com.metalservices.mvc.controllers.dtos.in;

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
public class CreateServiceOrderRequestDTOTest {

    LocalDateTime actualDate = LocalDateTime.now();

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void when_getServiceOrderNumber_withField_thenDontReturnExeption() {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);

        assertEquals("123", createServiceOrderRequestDTO.getServiceOrderNumber());
    }

    @Test
    public void when_getCreatedAt_withField_thenDontReturnExeption() {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);

        assertEquals(actualDate, createServiceOrderRequestDTO.getCreatedAt());
    }

    @Test
    public void when_setServiceOrderNumber_withField_thenDontReturnExeption() {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO(null, actualDate);
        createServiceOrderRequestDTO.setServiceOrderNumber("12345");

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_setServiceOrderNumber_withOutField_thenReturnExeption() {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);
        createServiceOrderRequestDTO.setServiceOrderNumber(null);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_setCreateAt_withField_thenDontReturnExeption(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", null);
        createServiceOrderRequestDTO.setCreatedAt(actualDate);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_setCreateAt_withOutField_thenReturnExeption(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);
        createServiceOrderRequestDTO.setCreatedAt(null);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_Construct_withOutFields(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO();

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_Construct_withCorrectFields(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_Construct_withEmptyServiceOrderNumberField(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("", actualDate);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.stream().filter(violation -> {
            if(violation.getMessageTemplate().equals("serviceOrderNumber cannot be blank.")){
                return true;
            }
            return false;
        }).count() == 1);
    }

    @Test
    public void when_Construct_withNullServiceOrderNumberField(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO(null, actualDate);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.stream().filter(violation -> {
            if(violation.getMessageTemplate().equals("serviceOrderNumber cannot be null.")){
                return true;
            }
            return false;
        }).count() == 1);
    }

    @Test
    public void when_Construct_withNullCreatedAtField(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", null);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        assertTrue(violations.stream().filter(violation -> {
            if(violation.getMessageTemplate().equals("createdAt cannot be null.")){
                return true;
            }
            return false;
        }).count() == 1);
    }

    @Test
    public void when_toEntityWithAllFields_then_ReturnAnEntity(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("123", actualDate);

        Set<ConstraintViolation<CreateServiceOrderRequestDTO>> violations = validator.validate(createServiceOrderRequestDTO);

        ServiceOrder serviceOrder = createServiceOrderRequestDTO.toEntity();

        assertEquals(serviceOrder.getServiceOrderNumber(), "123");
        assertEquals(serviceOrder.getCreatedAt(), actualDate);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_toEntityWithNullFields_then_IsNotValidated(){
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO(null, null);

        ServiceOrder serviceOrder = createServiceOrderRequestDTO.toEntity();

        Set<ConstraintViolation<ServiceOrder>> violations = validator.validate(serviceOrder);

        assertFalse(violations.isEmpty());
    }
}
