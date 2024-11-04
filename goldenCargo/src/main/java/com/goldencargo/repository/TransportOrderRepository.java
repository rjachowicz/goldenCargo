package com.goldencargo.repository;

import com.goldencargo.model.entities.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportOrderRepository extends JpaRepository<TransportOrder, Long> {
}