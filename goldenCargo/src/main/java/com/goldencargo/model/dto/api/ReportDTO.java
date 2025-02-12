package com.goldencargo.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private Long reportId;
    private String reportType;
    private Date dateGenerated;
    private String content;
    private String generatedBy;
}
