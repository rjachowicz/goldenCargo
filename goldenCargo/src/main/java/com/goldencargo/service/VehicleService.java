package com.goldencargo.service;

import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findByIsDeletedFalse();
    }

    public List<Vehicle> findAvailableVehicle() {
        return vehicleRepository.findAvailableVehicle();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public void createVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Long id, Vehicle vehicleDetails) {
        vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setVehicleType(vehicleDetails.getVehicleType());
            vehicle.setRegistrationNumber(vehicleDetails.getRegistrationNumber());
            vehicle.setMake(vehicleDetails.getMake());
            vehicle.setModel(vehicleDetails.getModel());
            vehicle.setYear(vehicleDetails.getYear());
            vehicle.setCapacity(vehicleDetails.getCapacity());
            vehicle.setStatus(vehicleDetails.getStatus());
            vehicle.setPurchaseDate(vehicleDetails.getPurchaseDate());
            vehicle.setLastServiceDate(vehicleDetails.getLastServiceDate());
            vehicle.setNextServiceDue(vehicleDetails.getNextServiceDue());
            vehicle.setMileage(vehicleDetails.getMileage());
            vehicle.setUpdatedAt(new java.util.Date());
            return vehicleRepository.save(vehicle);
        });
    }

    @Transactional
    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
