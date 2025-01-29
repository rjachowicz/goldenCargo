package com.goldencargo.controller.api;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.api.TransportOrderDetailsDTO;
import com.goldencargo.model.dto.web.ReportDataDTO;
import com.goldencargo.model.entities.User;
import com.goldencargo.service.ReportService;
import com.goldencargo.service.TransportOrderService;
import com.goldencargo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transport-orders")
public class ApiTransportOrderController {

    private final TransportOrderService transportOrderService;
    private final UserService userService;
    private final ReportService reportService;

    public ApiTransportOrderController(TransportOrderService transportOrderService,
                                       UserService userService,
                                       ReportService reportService) {
        this.transportOrderService = transportOrderService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/all")
    public List<TransportOrderDetailsDTO> getAllTransportOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return transportOrderService.getTransportOrders(userByUsername.getUserId(), null);
    }

    @GetMapping("/completed")
    public List<TransportOrderDetailsDTO> getCompletedTransportOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return transportOrderService.getTransportOrders(userByUsername.getUserId(), Status.COMPLETED.toString());
    }

    @GetMapping("/todo")
    public ReportDataDTO getTransportOrdersToDo(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return reportService.generateTransportOrderData(userByUsername.getUserId(), Status.NEW.toString());
    }

    private User getUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return userService.findUserByUsername(userName);
    }
}
