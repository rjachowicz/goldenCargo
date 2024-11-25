package com.goldencargo.repository;

import com.goldencargo.model.entities.VehicleRepairs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepairsRepository extends JpaRepository<VehicleRepairs, Long> {
}