package com.goldencargo.controller.web;

import com.goldencargo.component.EmailRequestWithIds;
import com.goldencargo.model.dto.web.ReportDataDTO;
import com.goldencargo.service.EmailService;
import com.goldencargo.service.ReportService;
import com.goldencargo.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final ReportService reportService;
    private final EmailService emailService;

    @Autowired
    public EmailController(ReportService reportService, EmailService emailService) {
        this.reportService = reportService;
        this.emailService = emailService;
    }

    @PostMapping("/send-pdf")
    public ResponseEntity<String> sendPdfEmail(@RequestBody EmailRequestWithIds request) {
        ReportDataDTO reportDataDTO = reportService.generateReport(request.getTransportId());

        byte[] pdfContent = PdfGenerator.generateReportPdf(reportDataDTO);

        emailService.sendEmailWithAttachment(request.getTo(),
                "Your Report",
                "Please find the attached report.",
                pdfContent
        );

        return ResponseEntity.ok("Email sent successfully!");
    }
}
