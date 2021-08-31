package com.metalservices.mvc.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "service_order_number")
    private String serviceOrderNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
