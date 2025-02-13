package com.goldencargo.controller.web;

import com.goldencargo.model.entities.TechnicalInspection;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.TechnicalInspectionService;
import com.goldencargo.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/technical-inspections")
public class TechnicalInspectionController {

    private static final String ALIAS = "ti";
    private final TechnicalInspectionService technicalInspectionService;
    private final VehicleService vehicleService;
    private final GenericService genericService;

    public TechnicalInspectionController(TechnicalInspectionService technicalInspectionService,
                                         VehicleService vehicleService,
                                         GenericService genericService) {
        this.technicalInspectionService = technicalInspectionService;
        this.vehicleService = vehicleService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllInspections(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "inspectionDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<TechnicalInspection> inspections = genericService.getFilteredAndSortedEntities(
                TechnicalInspection.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("technicalInspections", inspections);
        model.addAttribute("technicalInspection", new TechnicalInspection());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("results", TechnicalInspection.InspectionResult.values());
        return "technical-inspections/main";
    }

    @PostMapping("/create")
    public String createInspection(@ModelAttribute TechnicalInspection inspection) {
        technicalInspectionService.createInspection(inspection);
        return "redirect:/technical-inspections";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<TechnicalInspection> inspection = technicalInspectionService.getInspectionById(id);
        if (inspection.isPresent()) {
            model.addAttribute("technicalInspection", inspection.get());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            model.addAttribute("results", TechnicalInspection.InspectionResult.values());
            return "technical-inspections/edit";
        }
        return "redirect:/technical-inspections";
    }

    @PostMapping("/update/{id}")
    public String updateInspection(@PathVariable Long id, @ModelAttribute TechnicalInspection inspectionDetails) {
        technicalInspectionService.updateInspection(id, inspectionDetails);
        return "redirect:/technical-inspections";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<TechnicalInspection> inspection = technicalInspectionService.getInspectionById(id);
        inspection.ifPresent(value -> model.addAttribute("technicalInspection", value));
        return "technical-inspections/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        boolean isDeleted = technicalInspectionService.deleteInspection(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
