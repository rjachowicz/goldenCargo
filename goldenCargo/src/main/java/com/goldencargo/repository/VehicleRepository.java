package com.goldencargo.repository;

import com.goldencargo.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByIsDeletedFalse();

    @Query("SELECT v FROM Vehicle v where v.status = 'AVAILABLE' and v.isDeleted is false")
    List<Vehicle> findAvailableVehicle();

    @Modifying
    @Transactional
    @Query("UPDATE Vehicle b SET b.isDeleted = true WHERE b.vehicleId = :id")
    void softDelete(@Param("id") Long id);

}