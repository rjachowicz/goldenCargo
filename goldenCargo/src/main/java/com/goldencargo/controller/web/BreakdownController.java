package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Breakdown;
import com.goldencargo.service.BreakdownService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/breakdowns")
public class BreakdownController {

    private static final String ALIAS = "b";
    private final BreakdownService breakdownService;
    private final IncidentService incidentService;
    private final GenericService genericService;

    public BreakdownController(BreakdownService breakdownService, IncidentService incidentService, GenericService genericService) {
        this.breakdownService = breakdownService;
        this.incidentService = incidentService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllBreakdowns(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "repairDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Breakdown> breakdowns = genericService.getFilteredAndSortedEntities(
                Breakdown.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("breakdowns", breakdowns);
        model.addAttribute("breakdown", new Breakdown());
        model.addAttribute("incidents", incidentService.getAllIncidents());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterValue", filterValue);

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
        Breakdown breakdown = breakdownService.getBreakdownById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid breakdown ID: " + id));
        model.addAttribute("breakdown", breakdown);
        model.addAttribute("incidents", incidentService.getAllIncidents());
        return "breakdowns/edit";
    }

    @PostMapping("/update/{id}")
    public String updateBreakdown(@PathVariable Long id, @ModelAttribute Breakdown breakdownDetails) {
        breakdownService.updateBreakdown(id, breakdownDetails);
        return "redirect:/breakdowns";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Breakdown breakdown = breakdownService.getBreakdownById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid breakdown ID: " + id));
        model.addAttribute("breakdown", breakdown);
        return "breakdowns/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBreakdown(@PathVariable Long id) {
        return breakdownService.deleteBreakdown(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
