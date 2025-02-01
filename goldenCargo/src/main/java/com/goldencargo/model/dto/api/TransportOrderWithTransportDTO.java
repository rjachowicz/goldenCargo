package com.goldencargo.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrderWithTransportDTO extends TransportOrderAdvancedDetailsDTO {
    private Integer transportId;
    private String actualDeparture;
    private String actualArrival;
    private String notes;
    private String transportStatus;
}
