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
@Table(name = "vehicles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "registration_number", unique = true, nullable = false, length = 20)
    private String registrationNumber;

    @Column(name = "make", length = 50)
    private String make;

    @Column(name = "model", length = 50)
    private String model;

    @Column(name = "year")
    private Integer year;

    @Column(name = "capacity")
    private Double capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VehicleStatus status = VehicleStatus.NEW;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_service_date")
    private Date lastServiceDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_service_due")
    private Date nextServiceDue;

    @Column(name = "mileage")
    private Integer mileage;

    @OneToMany(mappedBy = "vehicle")
    private Set<DriverVehicle> driverVehicles = new HashSet<>();

    @OneToMany(mappedBy = "assignedVehicle")
    private Set<TransportOrder> transportOrders = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<VehicleRepairs> vehicleServices = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<ServiceSchedule> serviceSchedules = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<TechnicalInspection> technicalInspections = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<Incident> incidents = new HashSet<>();

    public enum VehicleStatus {
        NEW,
        AVAILABLE,
        IN_SERVICE,
        ASSIGNED,
        MAINTENANCE
    }

}
