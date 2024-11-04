package com.goldencargo.controller;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.service.DriverReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driver-reports")
public class DriverReportController {

    private final DriverReportService driverReportService;

    public DriverReportController(DriverReportService driverReportService) {
        this.driverReportService = driverReportService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverReport>> getAllDriverReports() {
        List<DriverReport> driverReports = driverReportService.getAllDriverReports();
        return new ResponseEntity<>(driverReports, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DriverReport> getDriverReportById(@PathVariable Long id) {
        Optional<DriverReport> driverReport = driverReportService.getDriverReportById(id);
        return driverReport.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<DriverReport> createDriverReport(@RequestBody DriverReport driverReport) {
        DriverReport createdDriverReport = driverReportService.createDriverReport(driverReport);
        return new ResponseEntity<>(createdDriverReport, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DriverReport> updateDriverReport(@PathVariable Long id, @RequestBody DriverReport driverReportDetails) {
        Optional<DriverReport> updatedDriverReport = driverReportService.updateDriverReport(id, driverReportDetails);
        return updatedDriverReport.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriverReport(@PathVariable Long id) {
        boolean isDeleted = driverReportService.deleteDriverReport(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}