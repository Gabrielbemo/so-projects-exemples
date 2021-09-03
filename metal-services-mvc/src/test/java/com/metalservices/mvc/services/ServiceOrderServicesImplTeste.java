package com.metalservices.mvc.services;

import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.repositories.ServiceOrderRepository;
import com.metalservices.mvc.services.impl.ServiceOrderServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceOrderServicesImplTeste {

    @InjectMocks
    private ServiceOrderServicesImpl service;

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @Test
    public void when_listWithSuccess_then_returnList() throws Exception {
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        ServiceOrder serviceOrder1 = new ServiceOrder(1L, "123", LocalDateTime.now());
        ServiceOrder serviceOrder2 = new ServiceOrder(2L, "231", LocalDateTime.now());
        ServiceOrder serviceOrder3 = new ServiceOrder(3L, "333", LocalDateTime.now());
        serviceOrders.add(serviceOrder1);
        serviceOrders.add(serviceOrder2);
        serviceOrders.add(serviceOrder3);

        when(serviceOrderRepository.findAll()).thenReturn(serviceOrders);


        List<ServiceOrder> listServiceOrders = service.list();

        assertEquals(listServiceOrders.size(), serviceOrders.size());

        assertEquals(serviceOrders.get(0).getId(), listServiceOrders.get(0).getId());
        assertEquals(serviceOrders.get(0).getServiceOrderNumber(), listServiceOrders.get(0).getServiceOrderNumber());
        assertEquals(serviceOrders.get(0).getCreatedAt(), listServiceOrders.get(0).getCreatedAt());
        assertEquals(serviceOrders.get(1).getId(), listServiceOrders.get(1).getId());
        assertEquals(serviceOrders.get(1).getServiceOrderNumber(), listServiceOrders.get(1).getServiceOrderNumber());
        assertEquals(serviceOrders.get(1).getCreatedAt(), listServiceOrders.get(2).getCreatedAt());
        assertEquals(serviceOrders.get(2).getId(), listServiceOrders.get(2).getId());
        assertEquals(serviceOrders.get(2).getServiceOrderNumber(), listServiceOrders.get(2).getServiceOrderNumber());
        assertEquals(serviceOrders.get(2).getCreatedAt(), listServiceOrders.get(2).getCreatedAt());
    }

    @Test
    public void when_listIsEmpty_then_returnEmpty(){
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();

        when(serviceOrderRepository.findAll()).thenReturn(serviceOrders);

        List<ServiceOrder> listServiceOrders = service.list();

        assertEquals(listServiceOrders.size(), serviceOrders.size());
    }

    @Test
    public void when_listThrowExeption_then_throwExeption() {
        when(serviceOrderRepository.findAll()).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> {
            service.list();
        });
    }

    @Test
    public void when_createWithSuccess_then_returnCreated() throws Exception {
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", LocalDateTime.now());

        when(serviceOrderRepository.save(serviceOrder)).thenReturn(null);
        when(serviceOrderRepository.findByServiceOrderNumber(serviceOrder.getServiceOrderNumber())).thenReturn(serviceOrder);


        ServiceOrder serviceOrderCreated = service.create(serviceOrder);

        assertEquals(serviceOrder.getId(), serviceOrderCreated.getId());
        assertEquals(serviceOrder.getServiceOrderNumber(), serviceOrderCreated.getServiceOrderNumber());
        assertEquals(serviceOrder.getCreatedAt(), serviceOrderCreated.getCreatedAt());

    }

    @Test
    public void when_createWithEmptyFields_then_returnExeption() throws Exception {
        ServiceOrder serviceOrder = new ServiceOrder(1L, "123", LocalDateTime.now());

        when(serviceOrderRepository.save(serviceOrder)).thenReturn(null);
        when(serviceOrderRepository.findByServiceOrderNumber(serviceOrder.getServiceOrderNumber())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> {
            service.create(serviceOrder);
        });
    }
}
