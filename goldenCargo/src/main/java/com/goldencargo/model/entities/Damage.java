package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "damages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Damage extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_id")
    private Long damageId;

    @ManyToOne
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;

    @Column(name = "description")
    private String description;

    @Column(name = "repair_cost")
    private Double repairCost;

    @Column(name = "insurance_claim")
    private Boolean insuranceClaim;

    @Column(name = "claim_number", length = 50)
    private String claimNumber;
}
