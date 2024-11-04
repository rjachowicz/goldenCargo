package com.goldencargo.repository;

import com.goldencargo.model.entities.DriverReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverReportRepository extends JpaRepository<DriverReport, Long> {
}