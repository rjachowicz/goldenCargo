package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "damages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_id")
    private Long damageId;

    @OneToOne
    @JoinColumn(name = "incident_id", nullable = false, unique = true)
    private Incident incident;

    @Column(name = "description")
    private String description;

    @Column(name = "repair_cost")
    private Double repairCost;

    @Column(name = "insurance_claim")
    private Boolean insuranceClaim;

    @Column(name = "claim_number", length = 50)
    private String claimNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
