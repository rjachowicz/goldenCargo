package com.goldencargo.repository;

import com.goldencargo.model.entities.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {
}