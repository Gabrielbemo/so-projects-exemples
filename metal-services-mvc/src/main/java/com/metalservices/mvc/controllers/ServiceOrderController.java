package com.metalservices.mvc.controllers;

import com.metalservices.mvc.controllers.dtos.in.CreateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.in.UpdateServiceOrderRequestDTO;
import com.metalservices.mvc.controllers.dtos.out.CreateServiceOrderResponseDTO;
import com.metalservices.mvc.controllers.dtos.out.ListServiceOrderResponseDTO;
import com.metalservices.mvc.controllers.exceptions.EntityDoesNotExistException;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validation;
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
    public ResponseEntity<CreateServiceOrderResponseDTO> create(@Valid @RequestBody final CreateServiceOrderRequestDTO createServiceOrderRequestDTO) throws Exception{
        ServiceOrder serviceOrder = serviceOrderServices.create(createServiceOrderRequestDTO.toEntity());

        return new ResponseEntity<CreateServiceOrderResponseDTO>(
                CreateServiceOrderResponseDTO.fromEntity(serviceOrder),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListServiceOrderResponseDTO> getById(@PathVariable final long id) throws EntityDoesNotExistException {
        ServiceOrder serviceOrder;
        try{
            serviceOrder = serviceOrderServices.getById(id);
        }catch (EntityNotFoundException e){
            throw new EntityDoesNotExistException("Entity with id "+id+" does not exist");
        }
        return new ResponseEntity<ListServiceOrderResponseDTO>(
                ListServiceOrderResponseDTO.fromEntity(serviceOrder),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final long id){
        serviceOrderServices.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable final long id,@Valid @RequestBody final UpdateServiceOrderRequestDTO updateServiceOrderRequestDTO) throws EntityDoesNotExistException {
        ServiceOrder serviceOrder;
        try{
            serviceOrder = serviceOrderServices.getById(id);
        }catch (EntityNotFoundException e){
            throw new EntityDoesNotExistException("Entity with id "+id+" does not exist");
        }
        serviceOrder.setServiceOrderNumber(updateServiceOrderRequestDTO.getServiceOrderNumber());
        serviceOrder.setCreatedAt(updateServiceOrderRequestDTO.getCreatedAt());
        serviceOrderServices.update(serviceOrder);

        return ResponseEntity.noContent().build();
    }
}
