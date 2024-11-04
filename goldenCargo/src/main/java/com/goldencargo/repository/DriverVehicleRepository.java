package com.goldencargo.repository;

import com.goldencargo.model.entities.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long> {
}