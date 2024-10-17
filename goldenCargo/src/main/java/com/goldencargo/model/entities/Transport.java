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
@Table(name = "transports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private Long transportId;

    @OneToOne
    @JoinColumn(name = "transport_order_id", nullable = false)
    private TransportOrder transportOrder;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_departure")
    private Date actualDeparture;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_arrival")
    private Date actualArrival;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

    @Column(name = "notes")
    private String notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "transport")
    private Set<ShippingDocument> shippingDocuments = new HashSet<>();

    public enum Status {
        NEW,
        IN_PROGRESS,
        COMPLETED,
        DELAYED,
        CANCELLED
    }

}
