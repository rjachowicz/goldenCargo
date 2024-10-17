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
public class Vehicle {

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
    private Status status = Status.AVAILABLE;

    @Temporal(TemporalType.DATE)
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_service_date")
    private Date lastServiceDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "next_service_due")
    private Date nextServiceDue;

    @Column(name = "mileage")
    private Integer mileage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "vehicle")
    private Set<DriverVehicle> driverVehicles = new HashSet<>();

    @OneToMany(mappedBy = "assignedVehicle")
    private Set<TransportOrder> transportOrders = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<VehicleService> vehicleServices = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<ServiceSchedule> serviceSchedules = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<TechnicalInspection> technicalInspections = new HashSet<>();

    @OneToMany(mappedBy = "vehicle")
    private Set<Incident> incidents = new HashSet<>();

    public enum Status {
        AVAILABLE,
        IN_SERVICE,
        ASSIGNED,
        MAINTENANCE
    }

}
