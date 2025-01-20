package com.goldencargo.controller.web;

import com.goldencargo.component.ReportData;
import com.goldencargo.service.DropboxService;
import com.goldencargo.service.ReportService;
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
import java.util.UUID;

@Controller
public class CreateReportController {

    private final DropboxService dropboxService;
    private final ReportService reportService;

    public CreateReportController(DropboxService dropboxService, ReportService reportService) {
        this.dropboxService = dropboxService;
        this.reportService = reportService;
    }

    @GetMapping("/create-report")
    public String generateReport(@RequestParam("transportId") Long transportId, Model model) {
        ReportData reportData = reportService.generateReport(transportId);
        model.addAttribute("report", reportData);
        return "generate/report-result";
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam Long transportId) {
        ReportData reportData = reportService.generateReport(transportId);
        String randomUuid = UUID.randomUUID().toString();
        byte[] pdfContent = PdfGenerator.generateReportPdf(reportData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report" + randomUuid + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/save-to-dropbox")
    public String saveToDropbox(@RequestParam("transportId") Integer transportId, Model model) {
        try {
            ReportData reportData = reportService.generateReport(Long.valueOf(transportId));
            String randomUuid = UUID.randomUUID().toString();

            byte[] pdfContent = PdfGenerator.generateReportPdf(reportData);

            try (InputStream inputStream = new ByteArrayInputStream(pdfContent)) {
                String dropboxPath = "/reports/report-" + randomUuid + ".pdf";
                dropboxService.uploadFile(dropboxPath, inputStream);
            }

            model.addAttribute("message", "Report saved successfully to Dropbox!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to save report to Dropbox: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/files";
    }
}
