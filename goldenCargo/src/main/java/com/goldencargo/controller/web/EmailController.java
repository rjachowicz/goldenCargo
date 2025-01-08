package com.goldencargo.controller.web;

import com.goldencargo.component.EmailRequestWithIds;
import com.goldencargo.model.dto.*;
import com.goldencargo.service.CreateReportService;
import com.goldencargo.service.EmailService;
import com.goldencargo.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final CreateReportService reportService;

    @Autowired
    public EmailController(EmailService emailService, CreateReportService reportService) {
        this.emailService = emailService;
        this.reportService = reportService;
    }

    @PostMapping("/send-pdf")
    public ResponseEntity<String> sendPdfEmail(@RequestBody EmailRequestWithIds request) {
        VehicleReportDTO vehicleReport = reportService.generateVehicleReport(request.getVehicleId());
        ClientReportDTO clientReport = reportService.generateClientReport(request.getClientId());

        List<TechnicalInspectionDTO> technicalInspections = reportService.getTechnicalInspections(request.getVehicleId());
        List<ServiceScheduleDTO> serviceSchedules = reportService.getServiceSchedules(request.getVehicleId());
        List<DriverVehicleDTO> driverHistory = reportService.getDriverHistory(request.getVehicleId());

        byte[] pdfContent = PdfGenerator.generateReportPdf(vehicleReport,
                clientReport,
                technicalInspections,
                serviceSchedules,
                driverHistory
        );

        emailService.sendEmailWithAttachment(request.getTo(),
                "Your Report",
                "Please find the attached report.",
                pdfContent
        );

        return ResponseEntity.ok("Email sent successfully!");
    }
}
