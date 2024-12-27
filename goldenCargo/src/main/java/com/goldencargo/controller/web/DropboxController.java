package com.goldencargo.controller.web;

import com.goldencargo.service.DropboxService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.List;

@Controller
public class DropboxController {

    private final DropboxService dropboxService;

    public DropboxController(DropboxService dropboxService) {
        this.dropboxService = dropboxService;
    }

    @GetMapping("/files")
    public String getDropboxFiles(Model model) {
        List<String> files = dropboxService.listFiles();
        model.addAttribute("files", files);
        return "files/repository";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("file") String filePath) {
        try {
            InputStream inputStream = dropboxService.downloadFile(filePath);
            byte[] fileContent = inputStream.readAllBytes();
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file: " + filePath, e);
        }
    }

    @GetMapping("/view")
    public ResponseEntity<InputStreamResource> viewFile(@RequestParam String file) {
        try {
            InputStream pdfStream = dropboxService.downloadFile(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename(file).build());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(pdfStream));
        } catch (Exception e) {
            throw new RuntimeException("Error viewing file: " + file, e);
        }
    }

}
