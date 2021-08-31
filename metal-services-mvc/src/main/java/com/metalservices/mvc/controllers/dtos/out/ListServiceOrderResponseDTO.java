package com.metalservices.mvc.controllers.dtos.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metalservices.mvc.entity.ServiceOrder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ListServiceOrderResponseDTO {
    private Long id;
    private String serviceOrderNumber;
    private LocalDateTime createdAt;

    public static ListServiceOrderResponseDTO fromEntity(final ServiceOrder serviceOrder){
        return ListServiceOrderResponseDTO.builder()
                .id(serviceOrder.getId())
                .serviceOrderNumber(serviceOrder.getServiceOrderNumber())
                .createdAt(serviceOrder.getCreatedAt())
                .build();
    }
}
