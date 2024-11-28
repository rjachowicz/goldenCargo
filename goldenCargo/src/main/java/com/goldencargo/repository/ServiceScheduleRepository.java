package com.goldencargo.repository;

import com.goldencargo.model.entities.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {
    List<ServiceSchedule> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE ServiceSchedule b SET b.isDeleted = true WHERE b.scheduleId = :id")
    void softDelete(@Param("id") Long id);
}