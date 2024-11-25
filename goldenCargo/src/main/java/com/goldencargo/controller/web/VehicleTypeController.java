package com.goldencargo.controller.web;

import com.goldencargo.model.entities.VehicleType;
import com.goldencargo.service.VehicleTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicle-types")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public String getAllVehicleTypes(Model model) {
        List<VehicleType> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
        model.addAttribute("vehicleTypes", vehicleTypes);
        return "vehicleTypes/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicleType", new VehicleType());
        model.addAttribute("formTitle", "Create New Vehicle Type");
        return "vehicleTypes/create";
    }

    @PostMapping("/create")
    public String createVehicleType(@ModelAttribute VehicleType vehicleType) {
        vehicleTypeService.createVehicleType(vehicleType);
        return "redirect:/vehicle-types";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<VehicleType> vehicleType = vehicleTypeService.getVehicleTypeById(id);
        if (vehicleType.isPresent()) {
            model.addAttribute("vehicleType", vehicleType.get());
            model.addAttribute("formTitle", "Edit Vehicle Type");
            return "vehicleTypes/edit";
        }
        return "redirect:/vehicle-types";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, VehicleType vehicleType) {
        vehicleTypeService.updateVehicleType(id, vehicleType);
        return "redirect:/vehicle-types";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<VehicleType> vehicleType = vehicleTypeService.getVehicleTypeById(id);
        if (vehicleType.isPresent()) {
            model.addAttribute("vehicleType", vehicleType.get());
            return "vehicleTypes/details";
        }
        return "redirect:/vehicle-types";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        boolean isDeleted = vehicleTypeService.deleteVehicleType(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
