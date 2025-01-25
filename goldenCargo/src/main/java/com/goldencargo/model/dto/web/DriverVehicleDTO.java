package com.goldencargo.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class DriverVehicleDTO {
    private Timestamp assignedDate;
    private Timestamp endDate;
    private String firstName;
    private String lastName;
    private String notes;
}
