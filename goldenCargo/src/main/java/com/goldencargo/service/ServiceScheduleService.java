package com.goldencargo.service;

import com.goldencargo.model.entities.ServiceSchedule;
import com.goldencargo.repository.ServiceScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceScheduleService {

    private final ServiceScheduleRepository serviceScheduleRepository;

    public ServiceScheduleService(ServiceScheduleRepository serviceScheduleRepository) {
        this.serviceScheduleRepository = serviceScheduleRepository;
    }

    public List<ServiceSchedule> getAllSchedules() {
        return serviceScheduleRepository.findByIsDeletedFalse();
    }

    public Optional<ServiceSchedule> getScheduleById(Long id) {
        return serviceScheduleRepository.findById(id);
    }

    public ServiceSchedule createSchedule(ServiceSchedule serviceSchedule) {
        return serviceScheduleRepository.save(serviceSchedule);
    }

    public Optional<ServiceSchedule> updateSchedule(Long id, ServiceSchedule scheduleDetails) {
        return serviceScheduleRepository.findById(id).map(schedule -> {
            schedule.setVehicle(scheduleDetails.getVehicle());
            schedule.setScheduledDate(scheduleDetails.getScheduledDate());
            schedule.setServiceType(scheduleDetails.getServiceType());
            schedule.setStatus(scheduleDetails.getStatus());
            schedule.setUpdatedAt(new java.util.Date());
            return serviceScheduleRepository.save(schedule);
        });
    }

    public boolean deleteSchedule(Long id) {
        if (serviceScheduleRepository.existsById(id)) {
            serviceScheduleRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
