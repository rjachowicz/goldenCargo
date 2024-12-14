package com.goldencargo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ServiceScheduleDTO {
    private Timestamp scheduledDate;
    private String serviceType;
    private String status;
}
