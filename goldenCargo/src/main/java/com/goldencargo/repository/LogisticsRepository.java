package com.goldencargo.repository;

import com.goldencargo.model.entities.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
}