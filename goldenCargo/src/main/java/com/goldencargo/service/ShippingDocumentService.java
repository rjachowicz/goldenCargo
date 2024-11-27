package com.goldencargo.service;

import com.goldencargo.model.entities.ShippingDocument;
import com.goldencargo.repository.ShippingDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingDocumentService {

    private final ShippingDocumentRepository shippingDocumentRepository;

    public ShippingDocumentService(ShippingDocumentRepository shippingDocumentRepository) {
        this.shippingDocumentRepository = shippingDocumentRepository;
    }

    public List<ShippingDocument> getAllDocuments() {
        return shippingDocumentRepository.findByIsDeletedFalse();
    }

    @Transactional
    public Optional<ShippingDocument> getDocumentById(Long id) {
        return shippingDocumentRepository.findById(id);
    }

    public ShippingDocument createDocument(ShippingDocument document) {
        return shippingDocumentRepository.save(document);
    }

    public Optional<ShippingDocument> updateDocument(Long id, ShippingDocument documentDetails) {
        return shippingDocumentRepository.findById(id).map(document -> {
            document.setTransport(documentDetails.getTransport());
            document.setDocumentType(documentDetails.getDocumentType());
            document.setDocumentNumber(documentDetails.getDocumentNumber());
            document.setIssueDate(documentDetails.getIssueDate());
            document.setFileUrl(documentDetails.getFileUrl());
            document.setUpdatedAt(new java.util.Date());
            return shippingDocumentRepository.save(document);
        });
    }

    public boolean deleteDocument(Long id) {
        if (shippingDocumentRepository.existsById(id)) {
            shippingDocumentRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
