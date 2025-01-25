package com.goldencargo.model.dto.web;

import com.goldencargo.model.entities.Incident;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.model.entities.VehicleRepairs;
import com.goldencargo.service.VehicleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleReportDTO {

    private Vehicle vehicle;
    private List<VehicleService> services;
    private List<VehicleRepairs> repairs;
    private List<Incident> incidents;
    private List<TransportOrder> transportOrders;
}
