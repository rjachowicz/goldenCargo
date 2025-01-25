package com.goldencargo.controller.web;

import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.dto.web.InvoiceDTO;
import com.goldencargo.model.dto.web.ReportDataDTO;
import com.goldencargo.model.entities.Client;
import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.service.ClientInvoiceService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class CreateReportController {

    private final DropboxService dropboxService;
    private final ReportService reportService;
    private final ClientInvoiceService clientInvoiceService;

    public CreateReportController(DropboxService dropboxService,
                                  ReportService reportService,
                                  ClientInvoiceService clientInvoiceService) {
        this.dropboxService = dropboxService;
        this.reportService = reportService;
        this.clientInvoiceService = clientInvoiceService;
    }

    @GetMapping("/create-report")
    public String showReport(@RequestParam("transportId") Long transportId,
                             Model model) {

        ReportDataDTO reportDataDTO = reportService.generateReport(transportId);

        ClientInvoice clientInvoice = new ClientInvoice();

        Client client = new Client();
        client.setClientId(Long.valueOf(reportDataDTO.getClientId()));
        clientInvoice.setClient(client);

        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientOrderId(Long.valueOf(reportDataDTO.getClientOrderId()));
        clientInvoice.setClientOrder(clientOrder);

        model.addAttribute("report", reportDataDTO);
        model.addAttribute("clientInvoices", clientInvoice);
        model.addAttribute("clientInvoice", new ClientInvoice());

        return "generate/report-result";
    }

    @GetMapping("/export-report")
    public ResponseEntity<byte[]> exportReport(@RequestParam Long transportId) {
        ReportDataDTO reportDataDTO = reportService.generateReport(transportId);
        String randomUuid = UUID.randomUUID().toString();
        byte[] pdfContent = PdfGenerator.generateReportPdf(reportDataDTO);

        HttpHeaders headers = getHttpHeaders(randomUuid, "report");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @GetMapping("/export-invoice")
    public ResponseEntity<byte[]> exportInvoice(@RequestParam Long clientOrderId, @RequestParam Long clientId) {
        InvoiceDTO invoiceDTO = reportService.generateInvoice(clientOrderId, clientId);
        String randomUuid = UUID.randomUUID().toString();
        byte[] pdfContent = PdfGenerator.generateInvoicePdf(invoiceDTO);
        HttpHeaders headers = getHttpHeaders(randomUuid,"FV-");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/generate")
    public String createClientInvoice(@ModelAttribute ClientInvoice clientInvoice,
                                      @RequestParam("clientOrderId") Long clientOrderId,
                                      @RequestParam("clientId") Long clientId,
                                      Model model) {
        clientInvoice.setPaymentStatus(PaymentStatus.NEW);
        clientInvoiceService.createClientInvoice(clientInvoice);
        InvoiceDTO invoiceDTO = reportService.generateInvoice(clientOrderId, clientId);
        model.addAttribute("invoice", invoiceDTO);
        return "generate/invoiceDetails";
    }

    @GetMapping("/check-invoice")
    public String checkInvoice(@RequestParam Long clientOrderId,
                               @RequestParam Long clientId,
                               Model model) {
        InvoiceDTO existingInvoice = reportService.generateInvoice(clientOrderId, clientId);

        if (existingInvoice != null) {
            model.addAttribute("invoice", existingInvoice);
            return "generate/invoiceDetails";
        } else {
            ClientInvoice clientInvoice = new ClientInvoice();

            Client client = new Client();
            client.setClientId(clientId);
            clientInvoice.setClient(client);

            ClientOrder clientOrder = new ClientOrder();
            clientOrder.setClientOrderId(clientOrderId);
            clientInvoice.setClientOrder(clientOrder);

            model.addAttribute("clientId", clientId);
            model.addAttribute("clientOrderId", clientOrderId);
            model.addAttribute("clientInvoice", clientInvoice);

            return "generate/create-invoice";
        }
    }

    @GetMapping("/invoice")
    public String getInvoice(@ModelAttribute("invoice") InvoiceDTO invoiceDTO,
                             Model model) {
        model.addAttribute("invoice", invoiceDTO);
        return "generate/invoiceDetails";
    }

    @PostMapping("/save-to-dropbox")
    public String saveToDropbox(@RequestParam("transportId") Integer transportId,
                                Model model) {
        try {
            ReportDataDTO reportDataDTO = reportService.generateReport(Long.valueOf(transportId));
            String randomUuid = UUID.randomUUID().toString();

            byte[] pdfContent = PdfGenerator.generateReportPdf(reportDataDTO);
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

    private static HttpHeaders getHttpHeaders(String randomUuid, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename + randomUuid + ".pdf");
        return headers;
    }
}
