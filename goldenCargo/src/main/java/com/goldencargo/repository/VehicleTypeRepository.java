package com.goldencargo.repository;

import com.goldencargo.model.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    List<VehicleType> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE VehicleType b SET b.isDeleted = true WHERE b.vehicleTypeId = :id")
    void softDelete(@Param("id") Long id);
}