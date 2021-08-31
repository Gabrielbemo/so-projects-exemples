package com.metalservices.mvc.controllers.dtos.out;

import com.metalservices.mvc.entity.ServiceOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateServiceOrderResponseDTO {
    private Long id;
    private String serviceOrderNumber;
    private LocalDateTime createdAt;

    public static CreateServiceOrderResponseDTO fromEntity(final ServiceOrder serviceOrder){
        return CreateServiceOrderResponseDTO.builder()
                .id(serviceOrder.getId())
                .serviceOrderNumber(serviceOrder.getServiceOrderNumber())
                .createdAt(serviceOrder.getCreatedAt())
                .build();
    }
}
