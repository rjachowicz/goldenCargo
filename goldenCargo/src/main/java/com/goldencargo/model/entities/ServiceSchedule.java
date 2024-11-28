package com.goldencargo.model.entities;

import com.goldencargo.model.data.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "service_schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSchedule extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_date")
    private Date scheduledDate;

    @Column(name = "service_type", length = 50)
    private String serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;
}
