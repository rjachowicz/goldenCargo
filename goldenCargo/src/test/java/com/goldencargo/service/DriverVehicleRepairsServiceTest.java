package com.goldencargo.service;

import com.goldencargo.model.entities.DriverVehicle;
import com.goldencargo.repository.DriverVehicleRepository;
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
public class DriverVehicleRepairsServiceTest {

    @Mock
    private DriverVehicleRepository driverVehicleRepository;

    @InjectMocks
    private DriverVehicleService driverVehicleService;

    private DriverVehicle driverVehicle;

    @BeforeEach
    public void setUp() {
        driverVehicle = new DriverVehicle();
        driverVehicle.setId(1L);
        driverVehicle.setAssignedDate(new Date());
        driverVehicle.setEndDate(new Date());
        driverVehicle.setStatus(DriverVehicle.RepairStatus.NEW);
        driverVehicle.setNotes("Test assignment");
        driverVehicle.setCreatedAt(new Date());
        driverVehicle.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllDriverVehicles() {
        List<DriverVehicle> driverVehicles = Collections.singletonList(driverVehicle);
        when(driverVehicleRepository.findAll()).thenReturn(driverVehicles);

        List<DriverVehicle> result = driverVehicleService.getAllDriverVehicles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(driverVehicle.getNotes(), result.getFirst().getNotes());
        verify(driverVehicleRepository, times(1)).findAll();
    }

    @Test
    public void testGetDriverVehicleById() {
        when(driverVehicleRepository.findById(1L)).thenReturn(Optional.of(driverVehicle));

        Optional<DriverVehicle> result = driverVehicleService.getDriverVehicleById(1L);

        assertTrue(result.isPresent());
        assertEquals(driverVehicle.getNotes(), result.get().getNotes());
        verify(driverVehicleRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateDriverVehicle() {
        when(driverVehicleRepository.save(driverVehicle)).thenReturn(driverVehicle);

        DriverVehicle result = driverVehicleService.createDriverVehicle(driverVehicle);

        assertNotNull(result);
        assertEquals(driverVehicle.getNotes(), result.getNotes());
        verify(driverVehicleRepository, times(1)).save(driverVehicle);
    }

    @Test
    public void testUpdateDriverVehicle() {
        when(driverVehicleRepository.findById(1L)).thenReturn(Optional.of(driverVehicle));
        when(driverVehicleRepository.save(driverVehicle)).thenReturn(driverVehicle);

        Optional<DriverVehicle> result = driverVehicleService.updateDriverVehicle(1L, driverVehicle);

        assertTrue(result.isPresent());
        assertEquals(driverVehicle.getNotes(), result.get().getNotes());
        verify(driverVehicleRepository, times(1)).findById(1L);
        verify(driverVehicleRepository, times(1)).save(driverVehicle);
    }

    @Test
    public void testDeleteDriverVehicle() {
        when(driverVehicleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(driverVehicleRepository).deleteById(1L);

        boolean result = driverVehicleService.deleteDriverVehicle(1L);

        assertTrue(result);
        verify(driverVehicleRepository, times(1)).existsById(1L);
        verify(driverVehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteDriverVehicle_NotFound() {
        when(driverVehicleRepository.existsById(1L)).thenReturn(false);

        boolean result = driverVehicleService.deleteDriverVehicle(1L);

        assertFalse(result);
        verify(driverVehicleRepository, times(1)).existsById(1L);
        verify(driverVehicleRepository, never()).deleteById(1L);
    }
}