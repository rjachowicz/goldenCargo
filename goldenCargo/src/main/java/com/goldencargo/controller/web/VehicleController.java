package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.VehicleService;
import com.goldencargo.service.VehicleTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleTypeService vehicleTypeService;

    public VehicleController(VehicleService vehicleService, VehicleTypeService vehicleTypeService) {
        this.vehicleService = vehicleService;
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicles/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("vehicleTypes", vehicleTypeService.getAllVehicleTypes());
        model.addAttribute("statuses", Vehicle.VehicleStatus.values());
        return "vehicles/create";
    }

    @PostMapping("/create")
    public String createVehicle(Vehicle vehicle) {
        vehicleService.createVehicle(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            model.addAttribute("vehicleTypes", vehicleTypeService.getAllVehicleTypes());
            model.addAttribute("statuses", Vehicle.VehicleStatus.values());
            return "vehicles/edit";
        }
        return "redirect:/vehicles";
    }

    @PostMapping("/update/{id}")
    public String updateVehicle(@PathVariable Long id, Vehicle vehicleDetails) {
        vehicleService.updateVehicle(id, vehicleDetails);
        return "redirect:/vehicles";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        vehicle.ifPresent(value -> model.addAttribute("vehicle", value));
        return "vehicles/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicles";
    }
}
