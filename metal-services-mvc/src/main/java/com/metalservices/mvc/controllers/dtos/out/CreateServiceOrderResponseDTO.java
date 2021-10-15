package com.metalservices.mvc.controllers.dtos.out;

import com.metalservices.mvc.entity.ServiceOrder;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceOrderResponseDTO {

    @NotNull(message = "id cannot be null.")
    private Long id;

    @NotNull(message = "serviceOrderNumber cannot be null.")
    @NotBlank(message = "serviceOrderNumber cannot be blank.")
    private String serviceOrderNumber;

    @NotNull(message = "createdAt cannot be null.")
    private LocalDateTime createdAt;

    public static CreateServiceOrderResponseDTO fromEntity(final ServiceOrder serviceOrder){
        return CreateServiceOrderResponseDTO.builder()
                .id(serviceOrder.getId())
                .serviceOrderNumber(serviceOrder.getServiceOrderNumber())
                .createdAt(serviceOrder.getCreatedAt())
                .build();
    }
}
