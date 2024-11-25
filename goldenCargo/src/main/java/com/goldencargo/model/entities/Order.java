package com.goldencargo.model.entities;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "client_order_id")
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name = "transport_order_id")
    private TransportOrder transportOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private InvoiceType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

}
