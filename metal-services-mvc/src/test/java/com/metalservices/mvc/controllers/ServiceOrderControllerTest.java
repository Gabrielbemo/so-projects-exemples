package com.metalservices.mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metalservices.mvc.controllers.dtos.in.CreateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.in.UpdateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.out.CreateServiceOrderResponseDTO;
import com.metalservices.mvc.controllers.dtos.out.SOErrorDTO;
import com.metalservices.mvc.entity.OSErrorCodes;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ServiceOrderControllerTest {

    @InjectMocks
    private ServiceOrderController controller;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ServiceOrderServices serviceOrderServices;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandle())
                .build();

        objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Test
    public void when_getByIdWithSuccess_andReturn1_expect_statusOk() throws Exception {
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", LocalDateTime.now());

        when(serviceOrderServices.getById(1)).thenReturn(serviceOrder);

        MvcResult result = mockMvc.perform(get("/service-orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ServiceOrder serviceOrderResult = objectMapper.readValue(result.getResponse().getContentAsString(), ServiceOrder.class);

        Assertions.assertEquals(serviceOrderResult.getId(), serviceOrder.getId());
        Assertions.assertEquals(serviceOrderResult.getServiceOrderNumber(), serviceOrder.getServiceOrderNumber());
        Assertions.assertEquals(serviceOrderResult.getCreatedAt(), serviceOrder.getCreatedAt());
    }

    @Test
    public void when_getByIdWithInexistenceId_expect_statusNotFound() throws Exception {
        ServiceOrder serviceOrder = new ServiceOrder(0L, "123", LocalDateTime.now());

        when(serviceOrderServices.getById(1)).thenThrow(new EntityNotFoundException());

        MvcResult result = mockMvc.perform(get("/service-orders/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);

        Assertions.assertEquals(errorResult.getCode(), HttpStatus.NOT_FOUND.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ENTITY_DOES_NOT_EXIST);
    }

    @Test
    public void when_listWithSuccess_andReturn3_expect_statusOk() throws Exception {
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        ServiceOrder serviceOrder1 = new ServiceOrder(1L, "123", LocalDateTime.now());
        ServiceOrder serviceOrder2 = new ServiceOrder(2L, "231", LocalDateTime.now());
        ServiceOrder serviceOrder3 = new ServiceOrder(3L, "333", LocalDateTime.now());
        serviceOrders.add(serviceOrder1);
        serviceOrders.add(serviceOrder2);
        serviceOrders.add(serviceOrder3);

        when(serviceOrderServices.list()).thenReturn(serviceOrders);

        MvcResult result = mockMvc.perform(get("/service-orders/"))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].serviceOrderNumber", equalTo("123")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].serviceOrderNumber", equalTo("231")))
                .andExpect(jsonPath("$.length()", equalTo(3)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void when_listWithSuccess_andReturn1_expect_statusOk() throws Exception {
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        ServiceOrder serviceOrder1 = new ServiceOrder(1L, "123", LocalDateTime.now());
        serviceOrders.add(serviceOrder1);

        when(serviceOrderServices.list()).thenReturn(serviceOrders);

        MvcResult result = mockMvc.perform(get("/service-orders/"))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].serviceOrderNumber", equalTo("123")))
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void when_listWithSuccess_andReturnEmpty_expect_statusOk() throws Exception {
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();

        when(serviceOrderServices.list()).thenReturn(serviceOrders);

        MvcResult result = mockMvc.perform(get("/service-orders/"))
                .andDo(print())
                .andExpect(jsonPath("$.length()", equalTo(0)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void when_deleteWithSuccess_expect_statusOk() throws Exception {
        doNothing().when(serviceOrderServices).delete(1L);

        MvcResult result = mockMvc.perform(delete("/service-orders/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void when_deleteWithIncorrectId_expect_status400() throws Exception {
        doThrow(new EmptyResultDataAccessException(String.format("No class com.metalservices.mvc.entity.ServiceOrder entity with id %s exists!", 0), 1))
                .when(serviceOrderServices)
                .delete(0L);

        MvcResult result = mockMvc.perform(delete("/service-orders/0"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    };

    @Test
    public void when_createWithSuccess_expect_statusCreated() throws Exception {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("001", LocalDateTime.parse("2088-10-10T00:00"));

        ServiceOrder serviceOrder = createServiceOrderRequestDTO.toEntity();

        when(serviceOrderServices.create(isA(ServiceOrder.class)))
                .thenReturn(new ServiceOrder(1L, "001", LocalDateTime.parse("2088-10-10T00:00")));

        MvcResult result = mockMvc.perform(post("/service-orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceOrder)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        ServiceOrder serviceOrderResult = objectMapper.readValue(result.getResponse().getContentAsString(), ServiceOrder.class);

        Assertions.assertEquals(serviceOrderResult.getId(), 1);
        Assertions.assertEquals(serviceOrderResult.getServiceOrderNumber(), createServiceOrderRequestDTO.getServiceOrderNumber());
        Assertions.assertEquals(serviceOrderResult.getCreatedAt(), createServiceOrderRequestDTO.getCreatedAt());
    }

    @Test
    public void when_createWithInvalidServiceOrderNumber_expect_statusBadRequest() throws Exception {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("", LocalDateTime.parse("2088-10-10T00:00"));

        ServiceOrder serviceOrder = createServiceOrderRequestDTO.toEntity();

        MvcResult result = mockMvc.perform(post("/service-orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceOrder)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);
        
        Assertions.assertEquals(errorResult.getCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS);
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getField(), "serviceOrderNumber");
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getMessage(), "serviceOrderNumber cannot be blank.");
    }

    @Test
    public void when_createWithInvalidCreatedAt_expect_statusBadRequest() throws Exception {
        CreateServiceOrderRequestDTO createServiceOrderRequestDTO = new CreateServiceOrderRequestDTO("001", null);

        ServiceOrder serviceOrder = createServiceOrderRequestDTO.toEntity();

        MvcResult result = mockMvc.perform(post("/service-orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceOrder)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);

        Assertions.assertEquals(errorResult.getCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS);
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getField(), "createdAt");
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getMessage(), "createdAt cannot be null.");
    }

    @Test
    public void when_updateWithSuccess_expect_statusNoContent() throws Exception {
        UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO = new UpdateServiceOrderRequestDTO("001", LocalDateTime.parse("2088-10-10T00:00"));

        ServiceOrder serviceOrder = updateServiceOrderRequestDTO.toEntity();

        when(serviceOrderServices.getById(1)).thenReturn(serviceOrder);
        doNothing().when(serviceOrderServices).update(isA(ServiceOrder.class));

        MvcResult result = mockMvc.perform(put("/service-orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateServiceOrderRequestDTO)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void when_updateWithInvalidServiceOrderNumber_expect_statusBadRequest() throws Exception {
        UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO = new UpdateServiceOrderRequestDTO("", LocalDateTime.parse("2088-10-10T00:00"));

        ServiceOrder serviceOrder = updateServiceOrderRequestDTO.toEntity();

        MvcResult result = mockMvc.perform(put("/service-orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateServiceOrderRequestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);

        Assertions.assertEquals(errorResult.getCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS);
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getField(), "serviceOrderNumber");
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getMessage(), "serviceOrderNumber cannot be blank.");
    }

    @Test
    public void when_updateWithInvalidCreatedAt_expect_statusBadRequest() throws Exception {
        UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO = new UpdateServiceOrderRequestDTO("001", null);

        ServiceOrder serviceOrder = updateServiceOrderRequestDTO.toEntity();

        MvcResult result = mockMvc.perform(put("/service-orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateServiceOrderRequestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);

        Assertions.assertEquals(errorResult.getCode(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ERROR_INVALID_ARGUMENTS);
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getField(), "createdAt");
        Assertions.assertEquals(errorResult.getFieldsErrors().get(0).getMessage(), "createdAt cannot be null.");
    }

    @Test
    public void when_updateWithInvalidId_expect_statusBadRequest() throws Exception {
        UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO = new UpdateServiceOrderRequestDTO("001", LocalDateTime.parse("2088-10-10T00:00"));

        ServiceOrder serviceOrder = updateServiceOrderRequestDTO.toEntity();

        when(serviceOrderServices.getById(0)).thenThrow(new EntityNotFoundException());

        MvcResult result = mockMvc.perform(put("/service-orders/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateServiceOrderRequestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        SOErrorDTO errorResult = objectMapper.readValue(result.getResponse().getContentAsString(), SOErrorDTO.class);

        Assertions.assertEquals(errorResult.getCode(), HttpStatus.NOT_FOUND.value());
        Assertions.assertEquals(errorResult.getError(), OSErrorCodes.OS_ENTITY_DOES_NOT_EXIST);
    }
}
