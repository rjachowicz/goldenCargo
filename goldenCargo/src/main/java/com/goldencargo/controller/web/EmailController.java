package com.goldencargo.controller.web;

import com.goldencargo.component.EmailRequestWithIds;
import com.goldencargo.service.EmailService;
import com.goldencargo.util.PdfGenerator;
import com.goldencargo.model.dto.ClientReportDTO;
import com.goldencargo.model.dto.VehicleReportDTO;
import com.goldencargo.service.CreateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        byte[] pdf = PdfGenerator.generateReportPdf(vehicleReport, clientReport);
        emailService.sendEmailWithAttachment(request.getTo(), "Your Report", "Please find the attached report.", pdf);

        return ResponseEntity.ok("Email sent successfully!");
    }
}
