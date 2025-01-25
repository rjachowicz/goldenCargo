package com.goldencargo.service;

import com.goldencargo.model.dto.web.*;
import com.goldencargo.model.entities.Client;
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

    public List<Client> getAllClients() {
        return createReportRepository.findAllClients();
    }

    public VehicleReportDTO generateVehicleReport(Long vehicleId) {
        return createReportRepository.findVehicleReport(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + vehicleId));
    }

    public ClientReportDTO generateClientReport(Long clientId) {
        return createReportRepository.findClientReport(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + clientId));
    }

    public List<TechnicalInspectionDTO> getTechnicalInspections(Long vehicleId) {
        return createReportRepository.findTechnicalInspections(vehicleId);
    }

    public List<ServiceScheduleDTO> getServiceSchedules(Long vehicleId) {
        return createReportRepository.findServiceSchedules(vehicleId);
    }

    public List<DriverVehicleDTO> getDriverHistory(Long vehicleId) {
        return createReportRepository.findDriverHistory(vehicleId);
    }
}
