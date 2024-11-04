package com.goldencargo.repository;

import com.goldencargo.model.entities.ClientInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInvoiceRepository extends JpaRepository<ClientInvoice, Long> {
}