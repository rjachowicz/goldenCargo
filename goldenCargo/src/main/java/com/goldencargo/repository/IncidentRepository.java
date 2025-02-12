package com.goldencargo.repository;

import com.goldencargo.model.dto.api.IncidentDTO;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.model.entities.User;
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

    @Query("""
               SELECT new com.goldencargo.model.dto.api.IncidentDTO(
                   i.incidentId,
                   i.incidentType,
                   i.date,
                   i.description,
                   i.vehicle.vehicleId,
                   i.vehicle.registrationNumber,
                   i.driver.driverId,
                   i.status
               )
               FROM Incident i
               WHERE i.driver.user.userId = :userId
                 AND i.isDeleted = false
            """)
    List<IncidentDTO> getIncidentsByReportedBy(@Param("userId") Long userId);
}