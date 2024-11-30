package com.goldencargo.controller.web;

import com.goldencargo.model.entities.DriverVehicle;
import com.goldencargo.service.DriverService;
import com.goldencargo.service.DriverVehicleService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/driver-vehicles")
public class DriverVehicleController {

    private final DriverVehicleService driverVehicleService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final GenericService genericService;

    private static final String ALIAS = "dv";

    public DriverVehicleController(DriverVehicleService driverVehicleService,
                                   DriverService driverService,
                                   VehicleService vehicleService,
                                   GenericService genericService) {
        this.driverVehicleService = driverVehicleService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllDriverVehicles(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "assignedDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "desc") String sortLogic,
            Model model) {

        List<DriverVehicle> driverVehicles = genericService.getFilteredAndSortedEntities(
                DriverVehicle.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("driverVehicles", driverVehicles);
        model.addAttribute("driverVehicle", new DriverVehicle());
        model.addAttribute("drivers", driverVehicleService.getDriversWithoutVehicles());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("statuses", DriverVehicle.RepairStatus.values());

        return "driver-vehicles/main";
    }

    @PostMapping("/create")
    public String createDriverVehicle(@ModelAttribute DriverVehicle driverVehicle) {
        driverVehicleService.createDriverVehicle(driverVehicle);
        return "redirect:/driver-vehicles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<DriverVehicle> driverVehicle = driverVehicleService.getDriverVehicleById(id);
        if (driverVehicle.isPresent()) {
            model.addAttribute("driverVehicle", driverVehicle.get());
            model.addAttribute("drivers", driverService.getAllDrivers());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            model.addAttribute("statuses", DriverVehicle.RepairStatus.values());
            return "driver-vehicles/edit :: editDriverVehicleModal";
        }
        return "redirect:/driver-vehicles";
    }

    @PostMapping("/update/{id}")
    public String updateDriverVehicle(@PathVariable Long id, @ModelAttribute DriverVehicle updatedDriverVehicle) {
        driverVehicleService.updateDriverVehicle(id, updatedDriverVehicle);
        return "redirect:/driver-vehicles";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<DriverVehicle> driverVehicle = driverVehicleService.getDriverVehicleById(id);
        driverVehicle.ifPresent(value -> model.addAttribute("driverVehicle", value));
        return "driver-vehicles/details :: detailsDriverVehicleModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriverVehicle(@PathVariable Long id) {
        boolean isDeleted = driverVehicleService.deleteDriverVehicle(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
