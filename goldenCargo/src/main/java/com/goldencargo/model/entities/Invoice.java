package com.goldencargo.model.entities;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "invoice_number", unique = true, nullable = false, length = 50)
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type", nullable = false)
    private InvoiceType invoiceType;

    @Column(name = "related_id")
    private Long relatedId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_issued", nullable = false)
    private Date dateIssued;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.NEW;
}
