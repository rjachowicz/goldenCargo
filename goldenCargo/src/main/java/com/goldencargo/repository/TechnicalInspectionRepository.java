package com.goldencargo.repository;

import com.goldencargo.model.entities.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalInspectionRepository extends JpaRepository<TechnicalInspection, Long> {
}