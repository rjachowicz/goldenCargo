package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "driver_vehicles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverVehicle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assigned_date", nullable = false)
    private Date assignedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RepairStatus status = RepairStatus.NEW;

    @Column(name = "notes")
    private String notes;

    public enum RepairStatus {
        NEW,
        IN_REPAIR,
        COMPLETED,
        CANCELLED
    }
}
