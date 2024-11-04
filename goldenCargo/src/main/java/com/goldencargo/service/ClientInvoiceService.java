package com.goldencargo.service;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.repository.ClientInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientInvoiceService {

    @Autowired
    private ClientInvoiceRepository clientInvoiceRepository;

    public List<ClientInvoice> getAllClientInvoices() {
        return clientInvoiceRepository.findAll();
    }

    public Optional<ClientInvoice> getClientInvoiceById(Long id) {
        return clientInvoiceRepository.findById(id);
    }

    public ClientInvoice createClientInvoice(ClientInvoice clientInvoice) {
        return clientInvoiceRepository.save(clientInvoice);
    }

    public Optional<ClientInvoice> updateClientInvoice(Long id, ClientInvoice clientInvoiceDetails) {
        return clientInvoiceRepository.findById(id).map(clientInvoice -> {
            clientInvoice.setClient(clientInvoiceDetails.getClient());
            clientInvoice.setClientOrder(clientInvoiceDetails.getClientOrder());
            clientInvoice.setInvoiceNumber(clientInvoiceDetails.getInvoiceNumber());
            clientInvoice.setDateIssued(clientInvoiceDetails.getDateIssued());
            clientInvoice.setTotalAmount(clientInvoiceDetails.getTotalAmount());
            clientInvoice.setDueDate(clientInvoiceDetails.getDueDate());
            clientInvoice.setPaymentStatus(clientInvoiceDetails.getPaymentStatus());
            clientInvoice.setUpdatedAt(new java.util.Date());
            return clientInvoiceRepository.save(clientInvoice);
        });
    }

    public boolean deleteClientInvoice(Long id) {
        if (clientInvoiceRepository.existsById(id)) {
            clientInvoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}