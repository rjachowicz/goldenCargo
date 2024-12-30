package com.goldencargo.controller.web;

import com.goldencargo.component.ReportData;
import com.goldencargo.model.dto.*;
import com.goldencargo.model.entities.Client;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.CreateReportService;
import com.goldencargo.service.DropboxService;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller
public class CreateReportController {

    private final CreateReportService createReportService;
    private final DropboxService dropboxService;

    public CreateReportController(CreateReportService createReportService, DropboxService dropboxService) {
        this.createReportService = createReportService;
        this.dropboxService = dropboxService;
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
        ReportData reportData = fetchReportData(vehicleId, clientId);

        model.addAttribute("clientReport", reportData.getClientReport());
        model.addAttribute("report", reportData.getVehicleReport());
        model.addAttribute("technicalInspections", reportData.getTechnicalInspections());
        model.addAttribute("serviceSchedules", reportData.getServiceSchedules());
        model.addAttribute("driverHistory", reportData.getDriverHistory());

        return "generate/report-result";
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam Long vehicleId, @RequestParam Long clientId) {
        ReportData reportData = fetchReportData(vehicleId, clientId);
        String randomUuid = UUID.randomUUID().toString();

        byte[] pdfContent = PdfGenerator.generateReportPdf(
                reportData.getVehicleReport(),
                reportData.getClientReport(),
                reportData.getTechnicalInspections(),
                reportData.getServiceSchedules(),
                reportData.getDriverHistory()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report" + randomUuid + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/save-to-dropbox")
    public String saveToDropbox(@RequestParam Long vehicleId, @RequestParam Long clientId, Model model) {
        try {
            ReportData reportData = fetchReportData(vehicleId, clientId);
            String randomUuid = UUID.randomUUID().toString();

            byte[] pdfContent = PdfGenerator.generateReportPdf(
                    reportData.getVehicleReport(),
                    reportData.getClientReport(),
                    reportData.getTechnicalInspections(),
                    reportData.getServiceSchedules(),
                    reportData.getDriverHistory()
            );

            try (InputStream inputStream = new ByteArrayInputStream(pdfContent)) {
                String dropboxPath = "/reports/report-" + randomUuid + ".pdf";
                dropboxService.uploadFile(dropboxPath, inputStream);
            }

            model.addAttribute("message", "Report saved successfully to Dropbox!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to save report to Dropbox: " + e.getMessage());
            e.printStackTrace();
        }

        return "generate/report-result";
    }

    private ReportData fetchReportData(Long vehicleId, Long clientId) {
        VehicleReportDTO vehicleReport = createReportService.generateVehicleReport(vehicleId);
        ClientReportDTO clientReport = createReportService.generateClientReport(clientId);
        List<TechnicalInspectionDTO> technicalInspections = createReportService.getTechnicalInspections(vehicleId);
        List<ServiceScheduleDTO> serviceSchedules = createReportService.getServiceSchedules(vehicleId);
        List<DriverVehicleDTO> driverHistory = createReportService.getDriverHistory(vehicleId);

        return new ReportData(vehicleReport, clientReport, technicalInspections, serviceSchedules, driverHistory);
    }

}
