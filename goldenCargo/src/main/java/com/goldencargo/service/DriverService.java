package com.goldencargo.service;

import com.goldencargo.model.entities.Driver;
import com.goldencargo.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> updateDriver(Long id, Driver driverDetails) {
        return driverRepository.findById(id).map(driver -> {
            driver.setUser(driverDetails.getUser());
            driver.setLicenseNumber(driverDetails.getLicenseNumber());
            driver.setLicenseCategory(driverDetails.getLicenseCategory());
            driver.setHireDate(driverDetails.getHireDate());
            driver.setDateOfBirth(driverDetails.getDateOfBirth());
            driver.setMedicalCertificateExpiry(driverDetails.getMedicalCertificateExpiry());
            driver.setDriverStatus(driverDetails.getDriverStatus());
            driver.setUpdatedAt(new java.util.Date());
            return driverRepository.save(driver);
        });
    }

    public boolean deleteDriver(Long id) {
        if (driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
            return true;
        }
        return false;
    }

}