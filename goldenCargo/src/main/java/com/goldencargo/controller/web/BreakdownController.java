package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Breakdown;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.service.BreakdownService;
import com.goldencargo.service.IncidentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/breakdowns")
public class BreakdownController {

    private final BreakdownService breakdownService;
    private final IncidentService incidentService;

    public BreakdownController(BreakdownService breakdownService, IncidentService incidentService) {
        this.breakdownService = breakdownService;
        this.incidentService = incidentService;
    }

    @GetMapping
    public String getAllBreakdowns(Model model) {
        List<Breakdown> breakdowns = breakdownService.getAllBreakdowns();
        model.addAttribute("breakdowns", breakdowns);
        return "breakdowns/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("breakdown", new Breakdown());
        model.addAttribute("incidents", incidentService.getAllIncidents());
        return "breakdowns/create";
    }

    @PostMapping("/create")
    public String createBreakdown(@ModelAttribute Breakdown breakdown) {
        breakdownService.createBreakdown(breakdown);
        return "redirect:/breakdowns";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Breakdown> breakdown = breakdownService.getBreakdownById(id);
        if (breakdown.isPresent()) {
            model.addAttribute("breakdown", breakdown.get());
            model.addAttribute("incidents", incidentService.getAllIncidents());
            return "breakdowns/edit";
        }
        return "redirect:/breakdowns";
    }

    @PostMapping("/update/{id}")
    public String updateBreakdown(@PathVariable Long id, Breakdown breakdownDetails) {
        breakdownService.updateBreakdown(id, breakdownDetails);
        return "redirect:/breakdowns";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Breakdown> breakdown = breakdownService.getBreakdownById(id);
        if (breakdown.isPresent()) {
            model.addAttribute("breakdown", breakdown.get());
            return "breakdowns/details";
        }
        return "redirect:/breakdowns";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBreakdown(@PathVariable Long id) {
        breakdownService.deleteBreakdown(id);
        return "redirect:/breakdowns";
    }
}
