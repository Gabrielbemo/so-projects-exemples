package com.metalservices.mvc.controllers.dtos.in;

import com.metalservices.mvc.entity.ServiceOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateServiceOrderRequestDTO {
    private String serviceOrderNumber;
    private LocalDateTime createdAt;

    public ServiceOrder toEntity(){
        return ServiceOrder.builder()
                .serviceOrderNumber(serviceOrderNumber)
                .createdAt(createdAt)
                .build();
    }
}
