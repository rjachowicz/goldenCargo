package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ShippingDocument;
import com.goldencargo.service.ShippingDocumentService;
import com.goldencargo.service.StorageService;
import com.goldencargo.service.TransportService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shipping-documents")
public class ShippingDocumentController {

    private final ShippingDocumentService shippingDocumentService;
    private final TransportService transportService;
    private final StorageService storageService;

    public ShippingDocumentController(ShippingDocumentService shippingDocumentService, TransportService transportService, StorageService storageService) {
        this.shippingDocumentService = shippingDocumentService;
        this.transportService = transportService;
        this.storageService = storageService;
    }

    @GetMapping
    public String getAllDocuments(Model model) {
        List<ShippingDocument> documents = shippingDocumentService.getAllDocuments();
        model.addAttribute("shippingDocuments", documents);
        return "shipping-documents/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shippingDocument", new ShippingDocument());
        model.addAttribute("transports", transportService.getAllTransports());
        return "shipping-documents/create";
    }

    @PostMapping("/create")
    public String createDocument(@ModelAttribute ShippingDocument document,
                                 @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileUrl = storageService.uploadFile(file);
                document.setFileUrl(fileUrl);
            }
            shippingDocumentService.createDocument(document);
        } catch (IOException e) {
            return "redirect:/shipping-documents/new?error=true";
        }
        return "redirect:/shipping-documents";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ShippingDocument> document = shippingDocumentService.getDocumentById(id);
        if (document.isPresent()) {
            model.addAttribute("shippingDocument", document.get());
            model.addAttribute("transports", transportService.getAllTransports());
            return "shipping-documents/edit";
        }
        return "redirect:/shipping-documents";
    }

    @PostMapping("/update/{id}")
    public String updateDocument(@PathVariable Long id,
                                 @ModelAttribute ShippingDocument documentDetails,
                                 @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileUrl = storageService.uploadFile(file);
                documentDetails.setFileUrl(fileUrl);
            }
            shippingDocumentService.updateDocument(id, documentDetails);
        } catch (IOException e) {
            return "redirect:/shipping-documents/edit/" + id + "?error=true";
        }
        return "redirect:/shipping-documents";
    }


    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ShippingDocument> document = shippingDocumentService.getDocumentById(id);
        document.ifPresent(value -> model.addAttribute("shippingDocument", value));
        return "shipping-documents/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        shippingDocumentService.deleteDocument(id);
        return "redirect:/shipping-documents";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        Optional<ShippingDocument> document = shippingDocumentService.getDocumentById(id);

        if (document.isPresent()) {
            String fileUrl = document.get().getFileUrl();
            try {
                Path filePath = Paths.get("/path/to/storage").resolve(fileUrl).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                if (!resource.exists()) {
                    throw new RuntimeException("File not found: " + fileUrl);
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } catch (IOException e) {
                throw new RuntimeException("Error loading file: " + fileUrl, e);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
