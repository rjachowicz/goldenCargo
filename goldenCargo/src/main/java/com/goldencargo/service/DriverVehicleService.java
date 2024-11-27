package com.goldencargo.service;

import com.goldencargo.model.entities.Driver;
import com.goldencargo.model.entities.DriverVehicle;
import com.goldencargo.repository.DriverVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverVehicleService {

    private final DriverVehicleRepository driverVehicleRepository;

    public DriverVehicleService(DriverVehicleRepository driverVehicleRepository) {
        this.driverVehicleRepository = driverVehicleRepository;
    }

    public List<DriverVehicle> getAllDriverVehicles() {
        return driverVehicleRepository.findByIsDeletedFalse();
    }

    public Optional<DriverVehicle> getDriverVehicleById(Long id) {
        return driverVehicleRepository.findById(id);
    }

    public DriverVehicle createDriverVehicle(DriverVehicle driverVehicle) {
        return driverVehicleRepository.save(driverVehicle);
    }

    public Optional<DriverVehicle> updateDriverVehicle(Long id, DriverVehicle driverVehicleDetails) {
        return driverVehicleRepository.findById(id).map(driverVehicle -> {
            driverVehicle.setDriver(driverVehicleDetails.getDriver());
            driverVehicle.setVehicle(driverVehicleDetails.getVehicle());
            driverVehicle.setAssignedDate(driverVehicleDetails.getAssignedDate());
            driverVehicle.setEndDate(driverVehicleDetails.getEndDate());
            driverVehicle.setStatus(driverVehicleDetails.getStatus());
            driverVehicle.setNotes(driverVehicleDetails.getNotes());
            driverVehicle.setUpdatedAt(new java.util.Date());
            return driverVehicleRepository.save(driverVehicle);
        });
    }

    public boolean deleteDriverVehicle(Long id) {
        if (driverVehicleRepository.existsById(id)) {
            driverVehicleRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public List<Driver> getDriversWithoutVehicles() {
        return driverVehicleRepository.findDriversWithoutVehicles();
    }

}