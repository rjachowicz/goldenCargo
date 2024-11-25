package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "vehicle_services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRepairs extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "service_date")
    private Date serviceDate;

    @Column(name = "service_type", length = 50)
    private String serviceType;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "service_center", length = 100)
    private String serviceCenter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_service_due")
    private Date nextServiceDue;

}
