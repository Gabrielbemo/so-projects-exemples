package com.metalservices.mvc.controllers;

import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ServiceOrderControllerTeste {

    @InjectMocks
    private ServiceOrderController controller;

    private MockMvc mockMvc;

    @Mock
    private ServiceOrderServices serviceOrderServices;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
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
}
