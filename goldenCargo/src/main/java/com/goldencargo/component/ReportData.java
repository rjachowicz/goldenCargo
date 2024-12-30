package com.goldencargo.component;

import com.goldencargo.model.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReportData {
    private final VehicleReportDTO vehicleReport;
    private final ClientReportDTO clientReport;
    private final List<TechnicalInspectionDTO> technicalInspections;
    private final List<ServiceScheduleDTO> serviceSchedules;
    private final List<DriverVehicleDTO> driverHistory;
}
