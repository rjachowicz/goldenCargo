package com.goldencargo.service;

import com.goldencargo.model.entities.Driver;
import com.goldencargo.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    private Driver driver;

    @BeforeEach
    public void setUp() {
        driver = new Driver();
        driver.setDriverId(1L);
        driver.setLicenseNumber("ABC12345");
        driver.setLicenseCategory("B");
        driver.setHireDate(new Date());
        driver.setDateOfBirth(new Date());
        driver.setMedicalCertificateExpiry(new Date());
        driver.setDriverStatus(Driver.DriverStatus.ACTIVE);
        driver.setCreatedAt(new Date());
        driver.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllDrivers() {
        List<Driver> drivers = Collections.singletonList(driver);
        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.getAllDrivers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(driver.getLicenseNumber(), result.getFirst().getLicenseNumber());
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    public void testGetDriverById() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        Optional<Driver> result = driverService.getDriverById(1L);

        assertTrue(result.isPresent());
        assertEquals(driver.getLicenseNumber(), result.get().getLicenseNumber());
        verify(driverRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateDriver() {
        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.createDriver(driver);

        assertNotNull(result);
        assertEquals(driver.getLicenseNumber(), result.getLicenseNumber());
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    public void testUpdateDriver() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);

        Optional<Driver> result = driverService.updateDriver(1L, driver);

        assertTrue(result.isPresent());
        assertEquals(driver.getLicenseNumber(), result.get().getLicenseNumber());
        verify(driverRepository, times(1)).findById(1L);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    public void testDeleteDriver() {
        when(driverRepository.existsById(1L)).thenReturn(true);
        doNothing().when(driverRepository).deleteById(1L);

        boolean result = driverService.deleteDriver(1L);

        assertTrue(result);
        verify(driverRepository, times(1)).existsById(1L);
        verify(driverRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteDriver_NotFound() {
        when(driverRepository.existsById(1L)).thenReturn(false);

        boolean result = driverService.deleteDriver(1L);

        assertFalse(result);
        verify(driverRepository, times(1)).existsById(1L);
        verify(driverRepository, never()).deleteById(1L);
    }
}