package com.goldencargo.repository;

import com.goldencargo.model.entities.Driver;
import com.goldencargo.model.entities.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, Long> {
    List<DriverVehicle> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE DriverVehicle b SET b.isDeleted = true WHERE b.id = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT d FROM Driver d WHERE d.driverId NOT IN (SELECT dv.driver.driverId FROM DriverVehicle dv WHERE dv.endDate IS NULL) AND d.isDeleted = false")
    List<Driver> findDriversWithoutVehicles();

}