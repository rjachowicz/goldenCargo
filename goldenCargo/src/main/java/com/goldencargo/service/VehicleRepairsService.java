package com.goldencargo.service;

import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleRepairsService {

    private final VehicleRepository vehicleRepository;

    public VehicleRepairsService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> updateVehicle(Long id, Vehicle vehicleDetails) {
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setRegistrationNumber(vehicleDetails.getRegistrationNumber());
            vehicle.setModel(vehicleDetails.getModel());
            vehicle.setMake(vehicleDetails.getMake());
            vehicle.setYear(vehicleDetails.getYear());
            vehicle.setUpdatedAt(new java.util.Date());
            return vehicleRepository.save(vehicle);
        });
    }

    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
