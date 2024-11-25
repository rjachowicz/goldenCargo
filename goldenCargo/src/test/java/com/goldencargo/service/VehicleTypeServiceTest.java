package com.goldencargo.service;

import com.goldencargo.model.entities.VehicleType;
import com.goldencargo.repository.VehicleTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleTypeServiceTest {

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @InjectMocks
    private VehicleTypeService vehicleTypeService;

    private VehicleType vehicleType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleType = new VehicleType();
        vehicleType.setVehicleTypeId(1L);
        vehicleType.setName("Truck");
        vehicleType.setDescription("Large transport vehicle");
    }

    @Test
    void getAllVehicleTypes() {
        vehicleTypeService.getAllVehicleTypes();
        verify(vehicleTypeRepository, times(1)).findAll();
    }

    @Test
    void getVehicleTypeById() {
        when(vehicleTypeRepository.findById(1L)).thenReturn(Optional.of(vehicleType));
        Optional<VehicleType> foundVehicleType = vehicleTypeService.getVehicleTypeById(1L);
        assertTrue(foundVehicleType.isPresent());
        assertEquals(vehicleType.getName(), foundVehicleType.get().getName());
    }

    @Test
    void createVehicleType() {
        when(vehicleTypeRepository.save(vehicleType)).thenReturn(vehicleType);
        VehicleType createdVehicleType = vehicleTypeService.createVehicleType(vehicleType);
        assertNotNull(createdVehicleType);
        assertEquals("Truck", createdVehicleType.getName());
    }

    @Test
    void updateVehicleType() {
        when(vehicleTypeRepository.findById(1L)).thenReturn(Optional.of(vehicleType));
        vehicleType.setDescription("Updated Description");
        when(vehicleTypeRepository.save(vehicleType)).thenReturn(vehicleType);
        Optional<VehicleType> updatedVehicleType = vehicleTypeService.updateVehicleType(1L, vehicleType);
        assertTrue(updatedVehicleType.isPresent());
        assertEquals("Updated Description", updatedVehicleType.get().getDescription());
    }

    @Test
    void deleteVehicleType() {
        when(vehicleTypeRepository.existsById(1L)).thenReturn(true);
        boolean isDeleted = vehicleTypeService.deleteVehicleType(1L);
        assertTrue(isDeleted);
        verify(vehicleTypeRepository, times(1)).deleteById(1L);
    }
}
