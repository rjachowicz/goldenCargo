package com.goldencargo.service;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.repository.DriverReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverReportService {

    private final DriverReportRepository driverReportRepository;

    public DriverReportService(DriverReportRepository driverReportRepository) {
        this.driverReportRepository = driverReportRepository;
    }

    public List<DriverReport> getAllDriverReports() {
        return driverReportRepository.findByIsDeletedFalse();
    }

    public Optional<DriverReport> getDriverReportById(Long id) {
        return driverReportRepository.findById(id);
    }

    public DriverReport createDriverReport(DriverReport driverReport) {
        return driverReportRepository.save(driverReport);
    }

    public Optional<DriverReport> updateDriverReport(Long id, DriverReport driverReportDetails) {
        return driverReportRepository.findById(id).map(driverReport -> {
            driverReport.setDriver(driverReportDetails.getDriver());
            driverReport.setDate(driverReportDetails.getDate());
            driverReport.setContent(driverReportDetails.getContent());
            driverReport.setUpdatedAt(new java.util.Date());
            return driverReportRepository.save(driverReport);
        });
    }

    public boolean deleteDriverReport(Long id) {
        if (driverReportRepository.existsById(id)) {
            driverReportRepository.softDelete(id);
            return true;
        }
        return false;
    }
}