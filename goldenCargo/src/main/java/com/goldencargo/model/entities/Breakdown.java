package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "breakdowns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Breakdown extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breakdown_id")
    private Long breakdownId;

    @OneToOne
    @JoinColumn(name = "incident_id", nullable = false, unique = true)
    private Incident incident;

    @Column(name = "description")
    private String description;

    @Column(name = "repair_cost", nullable = false)
    private Double repairCost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repair_date", nullable = false)
    private Date repairDate;
}
