package com.goldencargo.repository;

import com.goldencargo.model.entities.ShippingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShippingDocumentRepository extends JpaRepository<ShippingDocument, Long> {
    List<ShippingDocument> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE ShippingDocument b SET b.isDeleted = true WHERE b.documentId = :id")
    void softDelete(@Param("id") Long id);
}