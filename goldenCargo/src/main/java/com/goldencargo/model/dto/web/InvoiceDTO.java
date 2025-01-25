package com.goldencargo.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Long invoiceId;
    private LocalDateTime invoiceCreatedAt;
    private LocalDate dateIssued;
    private LocalDate dueDate;
    private String invoiceNumber;
    private String invoicePaymentStatus;
    private Double invoiceTotalAmount;

    private Long clientId;
    private String clientName;
    private String clientAddress;
    private String contactPerson;
    private String clientEmail;
    private String clientNip;
    private String clientPhone;

    private Long clientOrderId;
    private LocalDateTime clientOrderDate;
    private Double clientOrderTotalAmount;

    private Long transportOrderId;
    private String transportOrderName;
    private LocalDateTime scheduledDeparture;
    private LocalDateTime scheduledArrival;

    private Long transportId;
    private LocalDateTime actualDeparture;
    private LocalDateTime actualArrival;
    private String transportNotes;
}
