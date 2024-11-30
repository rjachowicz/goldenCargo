package com.goldencargo.controller.web;

import com.goldencargo.model.entities.VehicleRepairs;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.VehicleRepairsService;
import com.goldencargo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicle-repairs")
public class VehicleRepairsController {

    private final VehicleRepairsService vehicleRepairsService;
    private final VehicleService vehicleService;
    private final GenericService genericService;

    private static final String ALIAS = "vr";

    public VehicleRepairsController(VehicleRepairsService vehicleRepairsService, VehicleService vehicleService, GenericService genericService) {
        this.vehicleRepairsService = vehicleRepairsService;
        this.vehicleService = vehicleService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllRepairs(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "serviceDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<VehicleRepairs> repairs = genericService.getFilteredAndSortedEntities(
                VehicleRepairs.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("vehicleRepairs", repairs);
        model.addAttribute("vehicleRepair", new VehicleRepairs());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "vehicle-repairs/main";
    }

    @PostMapping("/create")
    public String createRepair(@ModelAttribute VehicleRepairs repair) {
        vehicleRepairsService.createRepair(repair);
        return "redirect:/vehicle-repairs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<VehicleRepairs> repair = vehicleRepairsService.getRepairById(id);
        if (repair.isPresent()) {
            model.addAttribute("vehicleRepair", repair.get());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            return "vehicle-repairs/edit :: editRepairModal";
        }
        return "redirect:/vehicle-repairs";
    }

    @PostMapping("/update/{id}")
    public String updateRepair(@PathVariable Long id, @ModelAttribute VehicleRepairs repairDetails) {
        vehicleRepairsService.updateRepair(id, repairDetails);
        return "redirect:/vehicle-repairs";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<VehicleRepairs> repair = vehicleRepairsService.getRepairById(id);
        repair.ifPresent(value -> model.addAttribute("vehicleRepair", value));
        return "vehicle-repairs/details :: detailsRepairModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        return vehicleRepairsService.deleteRepair(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
