package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transport-orders")
public class TransportOrderController {

    private final TransportOrderService transportOrderService;
    private final ClientOrderService clientOrderService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final LocationService locationService;

    public TransportOrderController(TransportOrderService transportOrderService,
                                    ClientOrderService clientOrderService,
                                    DriverService driverService,
                                    VehicleService vehicleService,
                                    LocationService locationService) {
        this.transportOrderService = transportOrderService;
        this.clientOrderService = clientOrderService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<TransportOrder> transportOrders = transportOrderService.getAllOrders();
        model.addAttribute("transportOrders", transportOrders);
        return "transport-orders/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transportOrder", new TransportOrder());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("statuses", Status.values());
        return "transport-orders/create";
    }

    @PostMapping("/create")
    public String createOrder(TransportOrder transportOrder) {
        transportOrderService.createOrder(transportOrder);
        return "redirect:/transport-orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<TransportOrder> transportOrder = transportOrderService.getOrderById(id);
        if (transportOrder.isPresent()) {
            model.addAttribute("transportOrder", transportOrder.get());
            model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
            model.addAttribute("drivers", driverService.getAllDrivers());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("statuses", Status.values());
            return "transport-orders/edit";
        }
        return "redirect:/transport-orders";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, TransportOrder orderDetails) {
        transportOrderService.updateOrder(id, orderDetails);
        return "redirect:/transport-orders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<TransportOrder> transportOrder = transportOrderService.getOrderById(id);
        transportOrder.ifPresent(value -> model.addAttribute("transportOrder", value));
        return "transport-orders/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        transportOrderService.deleteOrder(id);
        return "redirect:/transport-orders";
    }
}
