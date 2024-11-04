package com.goldencargo.controller;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.service.ClientInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-invoices")
public class ClientInvoiceController {

    private final ClientInvoiceService clientInvoiceService;

    public ClientInvoiceController(ClientInvoiceService clientInvoiceService) {
        this.clientInvoiceService = clientInvoiceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientInvoice>> getAllClientInvoices() {
        List<ClientInvoice> clientInvoices = clientInvoiceService.getAllClientInvoices();
        return new ResponseEntity<>(clientInvoices, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ClientInvoice> getClientInvoiceById(@PathVariable Long id) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        return clientInvoice.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<ClientInvoice> createClientInvoice(@RequestBody ClientInvoice clientInvoice) {
        ClientInvoice createdClientInvoice = clientInvoiceService.createClientInvoice(clientInvoice);
        return new ResponseEntity<>(createdClientInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientInvoice> updateClientInvoice(@PathVariable Long id, @RequestBody ClientInvoice clientInvoiceDetails) {
        Optional<ClientInvoice> updatedClientInvoice = clientInvoiceService.updateClientInvoice(id, clientInvoiceDetails);
        return updatedClientInvoice.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientInvoice(@PathVariable Long id) {
        boolean isDeleted = clientInvoiceService.deleteClientInvoice(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}