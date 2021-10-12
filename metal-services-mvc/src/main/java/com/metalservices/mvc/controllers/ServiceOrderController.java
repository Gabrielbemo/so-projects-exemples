package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.in.CreateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.in.UpdateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.out.CreateServiceOrderResponseDTO;
import com.metalservices.mvc.controllers.dtos.out.ListServiceOrderResponseDTO;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service-orders")
@Validated
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
    public ResponseEntity<CreateServiceOrderResponseDTO> create(@RequestBody final CreateServiceOrderRequestDTO createServiceOrderRequestDTO) throws Exception{
        ServiceOrder serviceOrder = serviceOrderServices.create(createServiceOrderRequestDTO.toEntity());

        return new ResponseEntity<CreateServiceOrderResponseDTO>(
                CreateServiceOrderResponseDTO.fromEntity(serviceOrder),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListServiceOrderResponseDTO> getById(@PathVariable final long id){
        return new ResponseEntity<ListServiceOrderResponseDTO>(
                ListServiceOrderResponseDTO.fromEntity(serviceOrderServices.getById(id)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final long id){
        serviceOrderServices.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable final long id, @RequestBody final UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO){
        ServiceOrder serviceOrder = serviceOrderServices.getById(id);
        serviceOrder.setServiceOrderNumber(updateServiceOrderRequestDTO.getServiceOrderNumber());
        serviceOrder.setCreatedAt(updateServiceOrderRequestDTO.getCreatedAt());
        serviceOrderServices.update(serviceOrder);

        return ResponseEntity.noContent().build();
    }
}
