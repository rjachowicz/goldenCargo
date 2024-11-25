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
@Table(name = "drivers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "license_number", unique = true, nullable = false, length = 50)
    private String licenseNumber;

    @Column(name = "license_category", length = 10)
    private String licenseCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "medical_certificate_expiry", nullable = false)
    private Date medicalCertificateExpiry;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_status", nullable = false)
    private DriverStatus driverStatus = DriverStatus.NEW;

    @OneToMany(mappedBy = "driver")
    private Set<DriverVehicle> driverVehicles = new HashSet<>();

    @OneToMany(mappedBy = "assignedDriver")
    private Set<TransportOrder> transportOrders = new HashSet<>();

    @OneToMany(mappedBy = "driver")
    private Set<DriverReport> driverReports = new HashSet<>();

    @OneToMany(mappedBy = "driver")
    private Set<Incident> incidents = new HashSet<>();

    public enum DriverStatus {
        NEW,
        ACTIVE,
        INACTIVE
    }
}
