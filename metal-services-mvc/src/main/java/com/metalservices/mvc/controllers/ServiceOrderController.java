package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.in.CreateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.out.CreateServiceOrderResponseDTO;
import com.metalservices.mvc.controllers.dtos.out.ListServiceOrderResponseDTO;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

    private ServiceOrderServices serviceOrderServices;

    @Autowired
    public ServiceOrderController(final ServiceOrderServices serviceOrderServices){
        this.serviceOrderServices = serviceOrderServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<ListServiceOrderResponseDTO>> list(){

        return new ResponseEntity<List<ListServiceOrderResponseDTO>>(
                serviceOrderServices.list().stream().map(serviceOrder -> ListServiceOrderResponseDTO.fromEntity(serviceOrder)).collect(Collectors.toList()),
                HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<CreateServiceOrderResponseDTO> create(@RequestBody @Valid final CreateServiceOrderRequestDTO createServiceOrderRequestDTO){
        ServiceOrder serviceOrder = serviceOrderServices.create(createServiceOrderRequestDTO.toEntity());

        return new ResponseEntity<CreateServiceOrderResponseDTO>(
                CreateServiceOrderResponseDTO.fromEntity(serviceOrder),
                HttpStatus.CREATED);
    }

    public void getById(){

    }

    public void delete(){

    }

    public void update(){

    }
}
