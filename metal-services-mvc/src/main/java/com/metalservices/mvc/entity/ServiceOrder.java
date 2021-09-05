package com.metalservices.mvc.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "serviceOrderNumber cannot be null.")
    @NotBlank(message = "serviceOrderNumber cannot be blank.")
    @Column(name = "service_order_number")
    private String serviceOrderNumber;

    @NotNull(message = "createdAt cannot be null.")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
