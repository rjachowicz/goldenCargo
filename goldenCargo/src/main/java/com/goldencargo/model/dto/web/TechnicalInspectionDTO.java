package com.goldencargo.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class TechnicalInspectionDTO {
    private Timestamp inspectionDate;
    private Timestamp nextInspectionDate;
    private String inspectorName;
    private String result;
    private String comments;
}
