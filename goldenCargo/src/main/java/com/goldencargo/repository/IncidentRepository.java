package com.goldencargo.repository;

import com.goldencargo.model.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Incident b SET b.isDeleted = true WHERE b.incidentId = :id")
    void softDelete(@Param("id") Long id);
}