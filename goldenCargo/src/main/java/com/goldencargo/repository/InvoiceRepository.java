package com.goldencargo.repository;

import com.goldencargo.model.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Invoice b SET b.isDeleted = true WHERE b.invoiceId = :id")
    void softDelete(@Param("id") Long id);
}