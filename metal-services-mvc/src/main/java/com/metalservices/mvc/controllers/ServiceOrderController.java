package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.in.CreateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.out.CreateServiceOrderResponseDTO;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

    private ServiceOrderServices serviceOrderServices;

    @Autowired
    public ServiceOrderController(final ServiceOrderServices serviceOrderServices){
        this.serviceOrderServices = serviceOrderServices;
    }

    @GetMapping("/")
    public List<ServiceOrder> getAll(){
        return serviceOrderServices.list();
    }

    @PostMapping("/")
    public CreateServiceOrderResponseDTO create(@RequestBody final CreateServiceOrderRequestDTO createServiceOrderRequestDTO){
        ServiceOrder serviceOrder = serviceOrderServices.create(createServiceOrderRequestDTO.toEntity());
        return CreateServiceOrderResponseDTO.fromEntity(serviceOrder);
    }

    public void getById(){

    }

    public void delete(){

    }

    public void update(){

    }
}
