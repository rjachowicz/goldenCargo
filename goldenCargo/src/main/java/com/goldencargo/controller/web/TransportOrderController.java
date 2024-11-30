package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final GenericService genericService;

    public TransportOrderController(TransportOrderService transportOrderService,
                                    ClientOrderService clientOrderService,
                                    DriverService driverService,
                                    VehicleService vehicleService,
                                    LocationService locationService,
                                    GenericService genericService) {
        this.transportOrderService = transportOrderService;
        this.clientOrderService = clientOrderService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.locationService = locationService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllOrders(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<TransportOrder> transportOrders = genericService.getFilteredAndSortedEntities(
                TransportOrder.class,
                "t",
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("transportOrders", transportOrders);
        model.addAttribute("transportOrder", new TransportOrder());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("statuses", Status.values());

        return "transport-orders/main";
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
            return "transport-orders/edit :: editTransportOrderModal";
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
        return "transport-orders/details :: detailsTransportOrderModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = transportOrderService.deleteOrder(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
