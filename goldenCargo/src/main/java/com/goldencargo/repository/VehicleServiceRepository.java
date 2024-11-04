package com.goldencargo.repository;

import com.goldencargo.model.entities.VehicleService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleServiceRepository extends JpaRepository<VehicleService, Long> {
}