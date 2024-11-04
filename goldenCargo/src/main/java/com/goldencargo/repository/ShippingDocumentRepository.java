package com.goldencargo.repository;

import com.goldencargo.model.entities.ShippingDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingDocumentRepository extends JpaRepository<ShippingDocument, Long> {
}