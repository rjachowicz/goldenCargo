package com.goldencargo.model.entities;

import com.goldencargo.model.data.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "transport_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrder extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_order_id")
    private Long transportOrderId;

    @ManyToOne
    @JoinColumn(name = "client_order_id", nullable = false)
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name = "assigned_driver_id")
    private Driver assignedDriver;

    @ManyToOne
    @JoinColumn(name = "assigned_vehicle_id")
    private Vehicle assignedVehicle;

    @ManyToOne
    @JoinColumn(name = "start_location_id")
    private Location startLocation;

    @ManyToOne
    @JoinColumn(name = "end_location_id")
    private Location endLocation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_departure")
    private Date scheduledDeparture;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_arrival")
    private Date scheduledArrival;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

    @OneToOne(mappedBy = "transportOrder")
    private Transport transport;
}
