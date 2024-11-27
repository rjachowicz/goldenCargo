package com.goldencargo.service;

import com.goldencargo.model.entities.VehicleType;
import com.goldencargo.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public List<VehicleType> getAllVehicleTypes() {
        return vehicleTypeRepository.findByIsDeletedFalse();
    }

    public Optional<VehicleType> getVehicleTypeById(Long id) {
        return vehicleTypeRepository.findById(id);
    }

    public VehicleType createVehicleType(VehicleType vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public Optional<VehicleType> updateVehicleType(Long id, VehicleType vehicleTypeDetails) {
        return vehicleTypeRepository.findById(id).map(vehicleType -> {
            vehicleType.setName(vehicleTypeDetails.getName());
            vehicleType.setDescription(vehicleTypeDetails.getDescription());
            vehicleType.setMaximumLoad(vehicleTypeDetails.getMaximumLoad());
            vehicleType.setDimensions(vehicleTypeDetails.getDimensions());
            vehicleType.setSpecialFeatures(vehicleTypeDetails.getSpecialFeatures());
            vehicleType.setUpdatedAt(new java.util.Date());
            return vehicleTypeRepository.save(vehicleType);
        });
    }

    public boolean deleteVehicleType(Long id) {
        if (vehicleTypeRepository.existsById(id)) {
            vehicleTypeRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
