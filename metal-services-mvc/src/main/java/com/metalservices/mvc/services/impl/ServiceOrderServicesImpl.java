package com.metalservices.mvc.services.impl;

import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.repositories.ServiceOrderRepository;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOrderServicesImpl implements ServiceOrderServices {

    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public ServiceOrderServicesImpl(final ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public List<ServiceOrder> list() {
        return serviceOrderRepository.findAll();
    }

    @Override
    public ServiceOrder create(ServiceOrder serviceOrder) {
        serviceOrderRepository.save(serviceOrder);
        return serviceOrderRepository.findByServiceOrderNumber(serviceOrder.getServiceOrderNumber());
    }
}
