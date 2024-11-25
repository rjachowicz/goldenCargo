package com.goldencargo.model.entities;

import com.goldencargo.model.data.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "client_invoices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientInvoice extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "client_order_id", nullable = false)
    private ClientOrder clientOrder;

    @Column(name = "invoice_number", unique = true, nullable = false, length = 50)
    private String invoiceNumber;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}
