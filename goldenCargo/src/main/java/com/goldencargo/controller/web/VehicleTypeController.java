package com.goldencargo.controller.web;

import com.goldencargo.model.entities.VehicleType;
import com.goldencargo.service.GenericService;
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

    private static final String ALIAS = "vt";
    private final VehicleTypeService vehicleTypeService;
    private final GenericService genericService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService, GenericService genericService) {
        this.vehicleTypeService = vehicleTypeService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllVehicleTypes(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<VehicleType> vehicleTypes = genericService.getFilteredAndSortedEntities(
                VehicleType.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("vehicleType", new VehicleType());
        return "vehicle-types/main";
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
            return "vehicle-types/edit :: editVehicleTypeModal";
        }
        return "redirect:/vehicle-types";
    }

    @PostMapping("/update/{id}")
    public String updateVehicleType(@PathVariable Long id, @ModelAttribute VehicleType vehicleType) {
        vehicleTypeService.updateVehicleType(id, vehicleType);
        return "redirect:/vehicle-types";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<VehicleType> vehicleType = vehicleTypeService.getVehicleTypeById(id);
        vehicleType.ifPresent(value -> model.addAttribute("vehicleType", value));
        return "vehicle-types/details :: detailsVehicleTypeModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        return vehicleTypeService.deleteVehicleType(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
