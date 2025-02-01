package com.goldencargo.controller.api;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.api.TransportOrderAdvancedDetailsDTO;
import com.goldencargo.model.dto.api.TransportOrderCreateRequest;
import com.goldencargo.model.dto.api.TransportOrderDetailsDTO;
import com.goldencargo.model.entities.Transport;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.model.entities.User;
import com.goldencargo.service.ReportService;
import com.goldencargo.service.TransportOrderService;
import com.goldencargo.service.TransportService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transport-orders")
public class ApiTransportOrderController {

    private final TransportOrderService transportOrderService;
    private final UserService userService;
    private final ReportService reportService;
    private final TransportService transportService;

    public ApiTransportOrderController(TransportOrderService transportOrderService,
                                       UserService userService,
                                       ReportService reportService,
                                       TransportService transportService) {
        this.transportOrderService = transportOrderService;
        this.userService = userService;
        this.reportService = reportService;
        this.transportService = transportService;
    }

    @GetMapping("/all")
    public List<TransportOrderDetailsDTO> getAllTransportOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return transportOrderService.getTransportOrders(userByUsername.getUserId());
    }

    @GetMapping("/todo")
    public TransportOrderAdvancedDetailsDTO getTransportOrdersToDo(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return reportService.generateTransportOrderData(userByUsername.getUserId(), Status.NEW.toString());
    }

    @GetMapping("/pending")
    public TransportOrderAdvancedDetailsDTO getTransportOrdersPending(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return reportService.generateTransportOrderDataWithTransport(userByUsername.getUserId(), Status.PENDING.toString());
    }

    @PostMapping(value = "/start-transport", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> startTransport(@RequestBody TransportOrderCreateRequest request) {

        Optional<TransportOrder> transportOrderOpt = transportOrderService.getOrderById(request.getTransportOrderId());
        if (transportOrderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transport order not found");
        }

        transportService.startTransport(request, transportOrderOpt);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transport started successfully");
    }

    @PostMapping(value = "/end-transport", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finishTransport(@RequestBody TransportOrderCreateRequest request) {

        Optional<Transport> transportOrderOpt = transportService.findByTransportOrderId(request.getTransportOrderId());
        if (transportOrderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transport order not found");
        }

        transportService.endTransport(request, transportOrderOpt);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transport ended successfully");
    }


    private User getUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return userService.findUserByUsername(userName);
    }
}
