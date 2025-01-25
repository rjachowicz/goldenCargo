package com.goldencargo.controller.api;

import com.goldencargo.model.dto.api.TransportOrderDetailsDTO;
import com.goldencargo.service.TransportOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transport-orders")
public class ApiTransportOrderController {

    private final TransportOrderService transportOrderService;

    public ApiTransportOrderController(TransportOrderService transportOrderService) {
        this.transportOrderService = transportOrderService;
    }

    @GetMapping("/get")
    public List<TransportOrderDetailsDTO> getTransportOrdersWithStatusNew() {
        return transportOrderService.getTransportOrderApi();
    }
}
