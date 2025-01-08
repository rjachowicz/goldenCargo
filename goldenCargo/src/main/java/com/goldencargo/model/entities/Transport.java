package com.goldencargo.model.entities;

import com.goldencargo.model.data.Status;
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
public class Transport extends AuditableEntity {

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

    @OneToMany(mappedBy = "transport")
    private Set<ShippingDocument> shippingDocuments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}
