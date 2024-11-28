package com.goldencargo.repository;

import com.goldencargo.model.entities.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TechnicalInspectionRepository extends JpaRepository<TechnicalInspection, Long> {

    List<TechnicalInspection> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE TechnicalInspection b SET b.isDeleted = true WHERE b.inspectionId = :id")
    void softDelete(@Param("id") Long id);
}