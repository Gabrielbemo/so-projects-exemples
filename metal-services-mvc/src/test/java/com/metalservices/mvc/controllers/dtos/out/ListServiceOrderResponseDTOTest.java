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
public class ListServiceOrderResponseDTOTest {
    LocalDateTime actualDate = LocalDateTime.now();

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void when_geId_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals(1L, listServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_getServiceOrderNumber_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals("123", listServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_getCreatedAt_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);

        assertEquals(actualDate, listServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_setId_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(null,"123", actualDate);
        listServiceOrderResponseDTO.setId(1L);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals(1L, listServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_setServiceOrderNumber_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,null, actualDate);
        listServiceOrderResponseDTO.setServiceOrderNumber("123");

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals("123", listServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_setCreatedAt_withField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", null);
        listServiceOrderResponseDTO.setCreatedAt(actualDate);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertTrue(violations.isEmpty());

        assertEquals(actualDate, listServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_setId_withOutField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);
        listServiceOrderResponseDTO.setId(null);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, listServiceOrderResponseDTO.getId());
    }

    @Test
    public void when_setServiceOrderNumber_withOutField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);
        listServiceOrderResponseDTO.setServiceOrderNumber(null);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, listServiceOrderResponseDTO.getServiceOrderNumber());
    }

    @Test
    public void when_setCreatedAt_withOutField_thenDontReturnExeption() {
        ListServiceOrderResponseDTO listServiceOrderResponseDTO = new ListServiceOrderResponseDTO(1L,"123", actualDate);
        listServiceOrderResponseDTO.setCreatedAt(null);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertFalse(violations.isEmpty());

        assertEquals(null, listServiceOrderResponseDTO.getCreatedAt());
    }

    @Test
    public void when_fromEntityWithAllFields_then_ReturnAnDTO(){
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", actualDate);

        ListServiceOrderResponseDTO listServiceOrderResponseDTO = ListServiceOrderResponseDTO.fromEntity(serviceOrder);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertEquals(listServiceOrderResponseDTO.getServiceOrderNumber(), "123");
        assertEquals(listServiceOrderResponseDTO.getCreatedAt(), actualDate);
        assertEquals(listServiceOrderResponseDTO.getId(), 1L);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_fromEntityWithNullFields_then_IsNotValidated(){
        ServiceOrder serviceOrder = new ServiceOrder(null, null, null);

        ListServiceOrderResponseDTO listServiceOrderResponseDTO = ListServiceOrderResponseDTO.fromEntity(serviceOrder);

        Set<ConstraintViolation<ListServiceOrderResponseDTO>> violations = validator.validate(listServiceOrderResponseDTO);

        assertEquals(listServiceOrderResponseDTO.getServiceOrderNumber(), null);
        assertEquals(listServiceOrderResponseDTO.getCreatedAt(), null);
        assertEquals(listServiceOrderResponseDTO.getId(), null);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void when_toStringOnBuilder_then_ReturnString(){

        var resultToString = ListServiceOrderResponseDTO.builder()
                .id(1L)
                .serviceOrderNumber("123")
                .createdAt(actualDate)
                .toString();


        assertEquals(resultToString,"ListServiceOrderResponseDTO.ListServiceOrderResponseDTOBuilder(id=1, serviceOrderNumber=123, createdAt=" + actualDate + ")");
    }
}
