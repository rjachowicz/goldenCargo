package com.goldencargo.model.entities;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.data.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrder extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_order_id")
    private Long clientOrderId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.NEW;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type")
    private InvoiceType invoiceType;

    @OneToMany(mappedBy = "clientOrder")
    private Set<TransportOrder> transportOrders = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "client_order_goods",
            joinColumns = @JoinColumn(name = "client_order_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id")
    )
    private Set<Goods> goods = new HashSet<>();

    @OneToMany(mappedBy = "clientOrder")
    private Set<ClientInvoice> clientInvoices = new HashSet<>();
}
