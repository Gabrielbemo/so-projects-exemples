package com.metalservices.mvc.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "service_order_number")
    private String serviceOrderNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
