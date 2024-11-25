package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_type", length = 50)
    private String reportType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_generated", nullable = false)
    private Date dateGenerated;

    @ManyToOne
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;

    @Column(name = "content")
    private String content;

}
