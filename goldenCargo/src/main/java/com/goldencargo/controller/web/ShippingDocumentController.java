package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ShippingDocument;
import com.goldencargo.service.DropboxService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.ShippingDocumentService;
import com.goldencargo.service.TransportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/shipping-documents")
public class ShippingDocumentController {

    private static final String ALIAS = "d";
    private final ShippingDocumentService shippingDocumentService;
    private final TransportService transportService;
    private final GenericService genericService;
    private final DropboxService dropboxService;

    public ShippingDocumentController(ShippingDocumentService shippingDocumentService,
                                      TransportService transportService,
                                      GenericService genericService,
                                      DropboxService dropboxService) {
        this.shippingDocumentService = shippingDocumentService;
        this.transportService = transportService;
        this.genericService = genericService;
        this.dropboxService = dropboxService;
    }

    @GetMapping
    public String getAllDocuments(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "documentType") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<ShippingDocument> documents = genericService.getFilteredAndSortedEntities(
                ShippingDocument.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("shippingDocuments", documents);
        model.addAttribute("shippingDocument", new ShippingDocument());
        model.addAttribute("transports", transportService.getAllTransports());
        return "shipping-documents/main";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        ShippingDocument document = shippingDocumentService.getDocumentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID: " + id));

        try {
            InputStream fileStream = dropboxService.downloadFile(document.getFileUrl());
            byte[] fileContent = fileStream.readAllBytes();
            String fileName = document.getFileUrl().substring(document.getFileUrl().lastIndexOf("/") + 1);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);

        } catch (Exception e) {
            throw new RuntimeException("Error while downloading file: " + e.getMessage(), e);
        }
    }

    @PostMapping("/create")
    public String createDocument(@ModelAttribute ShippingDocument document,
                                 @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                String dropboxPath = "/shipping/" + file.getOriginalFilename();
                String uploadedFilePath = dropboxService.uploadFile(dropboxPath, inputStream);

                document.setFileUrl(uploadedFilePath);
            }
            shippingDocumentService.createDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/shipping-documents/new?error=true";
        }
        return "redirect:/shipping-documents";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ShippingDocument document = shippingDocumentService.getDocumentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID: " + id));
        model.addAttribute("shippingDocument", document);
        model.addAttribute("transports", transportService.getAllTransports());
        return "shipping-documents/edit :: editShippingDocumentModal";
    }

    @PostMapping("/update/{id}")
    public String updateDocument(@PathVariable Long id,
                                 @ModelAttribute ShippingDocument documentDetails,
                                 @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                String dropboxPath = "/uploads/" + file.getOriginalFilename();
                String uploadedFilePath = dropboxService.uploadFile(dropboxPath, inputStream);

                documentDetails.setFileUrl(uploadedFilePath);
            }
            shippingDocumentService.updateDocument(id, documentDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/shipping-documents/edit/" + id + "?error=true";
        }
        return "redirect:/shipping-documents";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        ShippingDocument document = shippingDocumentService.getDocumentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID: " + id));
        model.addAttribute("shippingDocument", document);
        return "shipping-documents/details :: detailsShippingDocumentModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        if (shippingDocumentService.deleteDocument(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
