package com.goldencargo.repository;

import com.goldencargo.model.entities.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
}