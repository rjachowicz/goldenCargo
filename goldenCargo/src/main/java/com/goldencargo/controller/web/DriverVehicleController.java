package com.goldencargo.controller.web;

import com.goldencargo.model.entities.DriverVehicle;
import com.goldencargo.service.DriverService;
import com.goldencargo.service.DriverVehicleService;
import com.goldencargo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/driver-vehicles")
public class DriverVehicleController {

    private final DriverVehicleService driverVehicleService;
    private final DriverService driverService;
    private final VehicleService vehicleService;

    public DriverVehicleController(DriverVehicleService driverVehicleService,
                                   DriverService driverService,
                                   VehicleService vehicleService) {
        this.driverVehicleService = driverVehicleService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String listDriverVehicles(Model model) {
        model.addAttribute("driverVehicles", driverVehicleService.getAllDriverVehicles());
        return "driver-vehicles/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("driverVehicle", new DriverVehicle());
        model.addAttribute("drivers", driverVehicleService.getDriversWithoutVehicles());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("statuses", DriverVehicle.RepairStatus.values());
        return "driver-vehicles/create";
    }

    @PostMapping("/create")
    public String createDriverVehicle(@ModelAttribute("driverVehicle") DriverVehicle driverVehicle) {
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
            return "driver-vehicles/edit";
        }
        return "redirect:/driver-vehicles";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<DriverVehicle> driverVehicle = driverVehicleService.getDriverVehicleById(id);
        if (driverVehicle.isPresent()) {
            model.addAttribute("driverVehicle", driverVehicle.get());
            return "driver-vehicles/details";
        }
        return "redirect:/driver-vehicles";
    }


    @PostMapping("/update/{id}")
    public String updateDriverVehicle(@PathVariable Long id,
                                      @ModelAttribute("driverVehicle") DriverVehicle updatedDriverVehicle) {
        driverVehicleService.updateDriverVehicle(id, updatedDriverVehicle);
        return "redirect:/driver-vehicles";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDamage(@PathVariable Long id) {
        return driverVehicleService.deleteDriverVehicle(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
