package com.goldencargo.repository;

import com.goldencargo.model.entities.DriverReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverReportRepository extends JpaRepository<DriverReport, Long> {
    List<DriverReport> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE DriverReport b SET b.isDeleted = true WHERE b.driverReportId = :id")
    void softDelete(@Param("id") Long id);
}