package com.metalservices.mvc.controllers.dtos.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateServiceOrderResponseDTO {

    @NotNull(message = "id cannot be null.")
    private Long id;

    @NotNull(message = "serviceOrderNumber cannot be null.")
    @NotBlank(message = "serviceOrderNumber cannot be blank.")
    private String serviceOrderNumber;

    @NotNull(message = "createdAt cannot be null.")
    private LocalDateTime createdAt;
}
