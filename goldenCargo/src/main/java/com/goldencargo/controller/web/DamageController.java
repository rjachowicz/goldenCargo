package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Damage;
import com.goldencargo.service.DamageService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/damages")
public class DamageController {

    private static final String ALIAS = "d";
    private final DamageService damageService;
    private final IncidentService incidentService;
    private final GenericService genericService;

    public DamageController(DamageService damageService, IncidentService incidentService, GenericService genericService) {
        this.damageService = damageService;
        this.incidentService = incidentService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllDamages(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "description") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {
        List<Damage> damages = genericService.getFilteredAndSortedEntities(
                Damage.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("damages", damages);
        model.addAttribute("damage", new Damage());
        model.addAttribute("incidents", incidentService.getAllIncidents());
        return "damages/main";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("damage", new Damage());
        model.addAttribute("incidents", incidentService.getAllIncidents());
        return "damages/create";
    }

    @PostMapping("/create")
    public String createDamage(@ModelAttribute Damage damage) {
        damageService.createDamage(damage);
        return "redirect:/damages";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Damage damage = damageService.getDamageById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid damage ID: " + id));
        model.addAttribute("damage", damage);
        model.addAttribute("incidents", incidentService.getAllIncidents());
        return "damages/edit :: editDamageModal";
    }

    @PostMapping("/update/{id}")
    public String updateDamage(@PathVariable Long id, @ModelAttribute Damage damageDetails) {
        damageService.updateDamage(id, damageDetails);
        return "redirect:/damages";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Damage damage = damageService.getDamageById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid damage ID: " + id));
        model.addAttribute("damage", damage);
        return "damages/details :: detailsDamageModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDamage(@PathVariable Long id) {
        if (damageService.deleteDamage(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
