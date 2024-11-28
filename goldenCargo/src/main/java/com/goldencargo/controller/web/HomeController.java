package com.goldencargo.controller.web;

import com.goldencargo.service.OrderService;
import com.goldencargo.service.TransportService;
import com.goldencargo.service.UserService;
import com.goldencargo.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;
    private final TransportService transportService;
    private final VehicleService vehicleService;
    private final OrderService orderService;

    public HomeController(UserService userService, TransportService transportService, VehicleService vehicleService, OrderService orderService) {
        this.userService = userService;
        this.transportService = transportService;
        this.vehicleService = vehicleService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers());
        model.addAttribute("activeTransports", transportService.getAllTransports());
        model.addAttribute("totalVehicles", vehicleService.getAllVehicles());
        model.addAttribute("pendingOrders", orderService.getAllOrders());
        model.addAttribute("transportStatusData", transportService.getAllTransports());
        model.addAttribute("recentOrders", orderService.getAllOrders());
        return "home";
    }
}
