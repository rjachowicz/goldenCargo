package com.goldencargo.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestWithIds {
    private String to;
    private Long vehicleId;
    private Long clientId;
}
