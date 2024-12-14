package com.goldencargo.controller.web;

import com.goldencargo.model.dto.ClientReportDTO;
import com.goldencargo.model.dto.VehicleReportDTO;
import com.goldencargo.model.entities.Client;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.CreateReportService;
import com.goldencargo.util.PdfGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public String showReportForm(Model model) {
        List<Vehicle> vehicles = createReportService.getAllVehicles();
        List<Client> clients = createReportService.getAllClients();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("clients", clients);
        return "generate/report";
    }

    @PostMapping("/create-report")
    public String generateReport(@RequestParam(required = false) Long vehicleId,
                                 @RequestParam(required = false) Long clientId,
                                 Model model) {
        VehicleReportDTO report = createReportService.generateVehicleReport(vehicleId);
        ClientReportDTO clientReport = createReportService.generateClientReport(clientId);
        model.addAttribute("clientReport", clientReport);
        model.addAttribute("report", report);

        return "generate/report-result";
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam Long vehicleId, @RequestParam Long clientId) {
        VehicleReportDTO vehicleReport = createReportService.generateVehicleReport(vehicleId);
        ClientReportDTO clientReport = createReportService.generateClientReport(clientId);

        byte[] pdfContent = PdfGenerator.generateReportPdf(vehicleReport, clientReport);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }


}
