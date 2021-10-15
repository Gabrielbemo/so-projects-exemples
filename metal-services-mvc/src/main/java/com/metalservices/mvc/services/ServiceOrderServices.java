package com.metalservices.mvc.services;

import com.metalservices.mvc.entity.ServiceOrder;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface ServiceOrderServices {
    List<ServiceOrder> list();
    ServiceOrder create(ServiceOrder serviceOrder);
    void delete(long id) throws EmptyResultDataAccessException;
    ServiceOrder getById(long id);
    void update(ServiceOrder serviceOrder);
}
