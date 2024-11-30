package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.VehicleService;
import com.goldencargo.service.VehicleTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleTypeService vehicleTypeService;
    private final GenericService genericService;

    private static final String ALIAS = "v";

    public VehicleController(VehicleService vehicleService, VehicleTypeService vehicleTypeService, GenericService genericService) {
        this.vehicleService = vehicleService;
        this.vehicleTypeService = vehicleTypeService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllVehicles(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "registrationNumber") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Vehicle> vehicles = genericService.getFilteredAndSortedEntities(
                Vehicle.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("vehicleTypes", vehicleTypeService.getAllVehicleTypes());
        model.addAttribute("statuses", Vehicle.VehicleStatus.values());
        return "vehicles/main";
    }

    @PostMapping("/create")
    public String createVehicle(@ModelAttribute Vehicle vehicle) {
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
            return "vehicles/edit :: editVehicleModal";
        }
        return "redirect:/vehicles";
    }

    @PostMapping("/update/{id}")
    public String updateVehicle(@PathVariable Long id, @ModelAttribute Vehicle vehicleDetails) {
        vehicleService.updateVehicle(id, vehicleDetails);
        return "redirect:/vehicles";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        vehicle.ifPresent(value -> model.addAttribute("vehicle", value));
        return "vehicles/details :: detailsVehicleModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        return vehicleService.deleteVehicle(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
