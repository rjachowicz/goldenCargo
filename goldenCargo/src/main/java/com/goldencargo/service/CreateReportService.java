package com.goldencargo.service;

import com.goldencargo.model.dto.VehicleReportDTO;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.repository.CreateReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateReportService {

    private final CreateReportRepository createReportRepository;

    public CreateReportService(CreateReportRepository createReportRepository) {
        this.createReportRepository = createReportRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return createReportRepository.findAllVehicles();
    }

    public VehicleReportDTO generateVehicleReport(Long vehicleId) {
        return createReportRepository.findVehicleReport(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + vehicleId));
    }
}
