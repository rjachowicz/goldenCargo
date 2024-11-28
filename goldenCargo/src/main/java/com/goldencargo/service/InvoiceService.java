package com.goldencargo.service;

import com.goldencargo.model.entities.Invoice;
import com.goldencargo.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findByIsDeletedFalse();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> updateInvoice(Long id, Invoice invoiceDetails) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
            invoice.setInvoiceType(invoiceDetails.getInvoiceType());
            invoice.setRelatedId(invoiceDetails.getRelatedId());
            invoice.setDateIssued(invoiceDetails.getDateIssued());
            invoice.setTotalAmount(invoiceDetails.getTotalAmount());
            invoice.setDueDate(invoiceDetails.getDueDate());
            invoice.setPaymentStatus(invoiceDetails.getPaymentStatus());
            invoice.setUpdatedAt(new java.util.Date());
            return invoiceRepository.save(invoice);
        });
    }

    public boolean deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.softDelete(id);
            return true;
        }
        return false;
    }
}