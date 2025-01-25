package com.goldencargo.model.dto.api;

import com.goldencargo.model.data.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportOrderDetailsDTO {
    private Long transportOrderId;
    private String name;
    private String driverName;
    private String driverLicenseNumber;
    private String vehicleDetails;
    private String startLocation;
    private String endLocation;
    private String status;

    public TransportOrderDetailsDTO(
            Long transportOrderId,
            String name,
            String driverName,
            String driverLicenseNumber,
            String vehicleDetails,
            String startLocation,
            String endLocation,
            Status status
    ) {
        this.transportOrderId = transportOrderId;
        this.name = name;
        this.driverName = driverName;
        this.driverLicenseNumber = driverLicenseNumber;
        this.vehicleDetails = vehicleDetails;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.status = status.name();
    }

}
