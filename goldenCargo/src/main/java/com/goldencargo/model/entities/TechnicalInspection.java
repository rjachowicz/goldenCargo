package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "technical_inspections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalInspection extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspection_id")
    private Long inspectionId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inspection_date")
    private Date inspectionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private InspectionResult result = InspectionResult.NEW;

    @Column(name = "comments")
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_inspection_date")
    private Date nextInspectionDate;

    @Column(name = "inspector_name", length = 100)
    private String inspectorName;

    public enum InspectionResult {
        NEW,
        PASSED,
        FAILED
    }

}
