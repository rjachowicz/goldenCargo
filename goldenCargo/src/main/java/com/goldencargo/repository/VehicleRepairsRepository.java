package com.goldencargo.repository;

import com.goldencargo.model.entities.VehicleRepairs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VehicleRepairsRepository extends JpaRepository<VehicleRepairs, Long> {

    List<VehicleRepairs> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE VehicleRepairs b SET b.isDeleted = true WHERE b.serviceId = :id")
    void softDelete(@Param("id") Long id);
}