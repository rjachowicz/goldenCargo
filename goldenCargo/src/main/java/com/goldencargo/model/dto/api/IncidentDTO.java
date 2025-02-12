package com.goldencargo.model.dto.api;

import com.goldencargo.model.data.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {
    private Long incidentId;
    private String incidentType;
    private Date date;
    private String description;
    private Long vehicleId;
    private String vehicleRegNumber;
    private Long driverId;
    private Status status;
}
