package com.metalservices.mvc.services;

import com.metalservices.mvc.models.ServiceOrder;

import java.util.List;

public interface ServiceOrderServices {
    List<ServiceOrder> list();
    ServiceOrder create(ServiceOrder serviceOrder);
}
