package com.goldencargo.controller.web;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.entities.Invoice;
import com.goldencargo.service.DropboxService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.InvoiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final GenericService genericService;
    private final DropboxService dropboxService;

    private static final String ALIAS = "i";

    public InvoiceController(InvoiceService invoiceService, GenericService genericService, DropboxService dropboxService) {
        this.invoiceService = invoiceService;
        this.genericService = genericService;
        this.dropboxService = dropboxService;
    }

    @GetMapping
    public String getAllInvoices(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "invoiceNumber") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Invoice> invoices = genericService.getFilteredAndSortedEntities(
                Invoice.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("invoices", invoices);
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("invoiceTypes", InvoiceType.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "invoices/main";
    }

    @PostMapping("/create")
    public String createInvoice(
            @ModelAttribute Invoice invoice,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                String dropboxPath = "/invoices/" + file.getOriginalFilename();
                String uploadedFilePath = dropboxService.uploadFile(dropboxPath, inputStream);

                invoice.setFileUrl(uploadedFilePath);
            }
            invoiceService.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/invoices/new?error=true";
        }
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            model.addAttribute("invoice", invoice.get());
            model.addAttribute("invoiceTypes", InvoiceType.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "invoices/edit";
        }
        return "redirect:/invoices";
    }

    @PostMapping("/update/{id}")
    public String updateInvoice(
            @PathVariable Long id,
            @ModelAttribute Invoice invoiceDetails,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                String dropboxPath = "/invoices/" + file.getOriginalFilename();
                String uploadedFilePath = dropboxService.uploadFile(dropboxPath, inputStream);
                invoiceDetails.setFileUrl(uploadedFilePath);
            }
            invoiceService.updateInvoice(id, invoiceDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/invoices/edit/" + id + "?error=true";
        }
        return "redirect:/invoices";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invoice ID: " + id));

        try {
            InputStream fileStream = dropboxService.downloadFile(invoice.getFileUrl());
            byte[] fileContent = fileStream.readAllBytes();
            String fileName = invoice.getFileUrl().substring(invoice.getFileUrl().lastIndexOf("/") + 1);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file: " + e.getMessage(), e);
        }
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        invoice.ifPresent(value -> model.addAttribute("invoice", value));
        return "invoices/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        return invoiceService.deleteInvoice(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
