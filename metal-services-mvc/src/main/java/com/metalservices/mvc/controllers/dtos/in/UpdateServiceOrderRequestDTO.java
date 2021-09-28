package com.metalservices.mvc.controllers.dtos.in;

import com.metalservices.mvc.entity.ServiceOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateServiceOrderRequestDTO {

    @NotNull(message = "serviceOrderNumber cannot be null.")
    @NotBlank(message = "serviceOrderNumber cannot be blank.")
    private String serviceOrderNumber;

    @NotNull(message = "createdAt cannot be null.")
    private LocalDateTime createdAt;

    public ServiceOrder toEntity(){
        return ServiceOrder.builder()
                .serviceOrderNumber(serviceOrderNumber)
                .createdAt(createdAt)
                .build();
    }
}
