package com.goldencargo.controller.web;

import com.goldencargo.model.dto.VehicleReportDTO;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.CreateReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CreateReportController {

    private final CreateReportService createReportService;

    public CreateReportController(CreateReportService createReportService) {
        this.createReportService = createReportService;
    }

    @GetMapping("/create-report")
    public String generateVehicleReportForm(Model model) {
        List<Vehicle> vehicles = createReportService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "generate/report";
    }

    @PostMapping("/create-report")
    public String generateVehicleReport(@RequestParam Long vehicleId, Model model) {
        VehicleReportDTO report = createReportService.generateVehicleReport(vehicleId);
        model.addAttribute("report", report);
        return "generate/report-result";
    }
}
