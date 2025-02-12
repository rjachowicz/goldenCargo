package com.goldencargo.repository;

import com.goldencargo.model.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByIsDeletedFalse();

    @Query("SELECT dr FROM Driver dr where dr.driverStatus = 'AVAILABLE' and dr.isDeleted is false")
    List<Driver> findAvailableDrivers();

    @Query("SELECT dr FROM Driver dr where dr.isDeleted is false and dr.user.userId = :id")
    Optional<Driver> findDriverByUserId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Driver b SET b.isDeleted = true WHERE b.driverId = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE from Driver l where l.driverId = :id")
    void forceDelete(@Param("id") Long id);
}