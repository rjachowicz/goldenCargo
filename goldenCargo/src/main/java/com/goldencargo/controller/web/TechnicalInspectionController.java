package com.goldencargo.controller.web;

import com.goldencargo.model.entities.TechnicalInspection;
import com.goldencargo.service.TechnicalInspectionService;
import com.goldencargo.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/technical-inspections")
public class TechnicalInspectionController {

    private final TechnicalInspectionService technicalInspectionService;
    private final VehicleService VehicleService;

    public TechnicalInspectionController(TechnicalInspectionService technicalInspectionService,
                                         VehicleService VehicleService) {
        this.technicalInspectionService = technicalInspectionService;
        this.VehicleService = VehicleService;
    }

    @GetMapping
    public String getAllInspections(Model model) {
        List<TechnicalInspection> inspections = technicalInspectionService.getAllInspections();
        model.addAttribute("technicalInspections", inspections);
        return "technical-inspections/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("technicalInspection", new TechnicalInspection());
        model.addAttribute("vehicles", VehicleService.getAllVehicles());
        model.addAttribute("results", TechnicalInspection.InspectionResult.values());
        return "technical-inspections/create";
    }

    @PostMapping("/create")
    public String createInspection(TechnicalInspection inspection) {
        technicalInspectionService.createInspection(inspection);
        return "redirect:/technical-inspections";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<TechnicalInspection> inspection = technicalInspectionService.getInspectionById(id);
        if (inspection.isPresent()) {
            model.addAttribute("technicalInspection", inspection.get());
            model.addAttribute("vehicles", VehicleService.getAllVehicles());
            model.addAttribute("results", TechnicalInspection.InspectionResult.values());
            return "technical-inspections/edit";
        }
        return "redirect:/technical-inspections";
    }

    @PostMapping("/update/{id}")
    public String updateInspection(@PathVariable Long id, TechnicalInspection inspectionDetails) {
        technicalInspectionService.updateInspection(id, inspectionDetails);
        return "redirect:/technical-inspections";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<TechnicalInspection> inspection = technicalInspectionService.getInspectionById(id);
        inspection.ifPresent(value -> model.addAttribute("technicalInspection", value));
        return "technical-inspections/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteInspection(@PathVariable Long id) {
        technicalInspectionService.deleteInspection(id);
        return "redirect:/technical-inspections";
    }
}
