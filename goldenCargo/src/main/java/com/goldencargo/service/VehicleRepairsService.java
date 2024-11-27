package com.goldencargo.service;

import com.goldencargo.model.entities.VehicleRepairs;
import com.goldencargo.repository.VehicleRepairsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleRepairsService {

    private final VehicleRepairsRepository vehicleRepairsRepository;

    public VehicleRepairsService(VehicleRepairsRepository vehicleRepairsRepository) {
        this.vehicleRepairsRepository = vehicleRepairsRepository;
    }

    public List<VehicleRepairs> getAllRepairs() {
        return vehicleRepairsRepository.findByIsDeletedFalse();
    }

    public Optional<VehicleRepairs> getRepairById(Long id) {
        return vehicleRepairsRepository.findById(id);
    }

    public VehicleRepairs createRepair(VehicleRepairs repair) {
        return vehicleRepairsRepository.save(repair);
    }

    public Optional<VehicleRepairs> updateRepair(Long id, VehicleRepairs repairDetails) {
        return vehicleRepairsRepository.findById(id).map(repair -> {
            repair.setVehicle(repairDetails.getVehicle());
            repair.setServiceDate(repairDetails.getServiceDate());
            repair.setServiceType(repairDetails.getServiceType());
            repair.setDescription(repairDetails.getDescription());
            repair.setCost(repairDetails.getCost());
            repair.setServiceCenter(repairDetails.getServiceCenter());
            repair.setNextServiceDue(repairDetails.getNextServiceDue());
            repair.setUpdatedAt(new java.util.Date());
            return vehicleRepairsRepository.save(repair);
        });
    }

    public boolean deleteRepair(Long id) {
        if (vehicleRepairsRepository.existsById(id)) {
            vehicleRepairsRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
