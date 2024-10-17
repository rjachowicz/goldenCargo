package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_order_id")
    private Long clientOrderId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "clientOrder")
    private Set<TransportOrder> transportOrders = new HashSet<>();

    @OneToMany(mappedBy = "clientOrder")
    private Set<Goods> goods = new HashSet<>();

    @OneToMany(mappedBy = "clientOrder")
    private Set<ClientInvoice> clientInvoices = new HashSet<>();

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    public enum PaymentStatus {
        PAID,
        UNPAID,
        PARTIAL
    }

}
