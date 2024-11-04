package com.goldencargo.controller;

import com.goldencargo.model.entities.DriverVehicle;
import com.goldencargo.service.DriverVehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driver-vehicles")
public class DriverVehicleController {

    private final DriverVehicleService driverVehicleService;

    public DriverVehicleController(DriverVehicleService driverVehicleService) {
        this.driverVehicleService = driverVehicleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverVehicle>> getAllDriverVehicles() {
        List<DriverVehicle> driverVehicles = driverVehicleService.getAllDriverVehicles();
        return new ResponseEntity<>(driverVehicles, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DriverVehicle> getDriverVehicleById(@PathVariable Long id) {
        Optional<DriverVehicle> driverVehicle = driverVehicleService.getDriverVehicleById(id);
        return driverVehicle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<DriverVehicle> createDriverVehicle(@RequestBody DriverVehicle driverVehicle) {
        DriverVehicle createdDriverVehicle = driverVehicleService.createDriverVehicle(driverVehicle);
        return new ResponseEntity<>(createdDriverVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DriverVehicle> updateDriverVehicle(@PathVariable Long id, @RequestBody DriverVehicle driverVehicleDetails) {
        Optional<DriverVehicle> updatedDriverVehicle = driverVehicleService.updateDriverVehicle(id, driverVehicleDetails);
        return updatedDriverVehicle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriverVehicle(@PathVariable Long id) {
        boolean isDeleted = driverVehicleService.deleteDriverVehicle(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}