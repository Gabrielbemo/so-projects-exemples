package com.metalservices.mvc.repositories;

import com.metalservices.mvc.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    ServiceOrder findByServiceOrderNumber(final String serviceOrderNumber);
}
