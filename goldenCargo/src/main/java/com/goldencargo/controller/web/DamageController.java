package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Damage;
import com.goldencargo.service.DamageService;
import com.goldencargo.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/damages")
public class DamageController {

    private final DamageService damageService;
    private final IncidentService incidentService;

    public DamageController(DamageService damageService, IncidentService incidentService) {
        this.damageService = damageService;
        this.incidentService = incidentService;
    }

    @GetMapping
    public String getAllDamages(Model model) {
        List<Damage> damages = damageService.getAllDamages();
        model.addAttribute("damages", damages);
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
        Optional<Damage> damage = damageService.getDamageById(id);
        if (damage.isPresent()) {
            model.addAttribute("damage", damage.get());
            model.addAttribute("incidents", incidentService.getAllIncidents());
            return "damages/edit";
        }
        return "redirect:/damages";
    }

    @PostMapping("/update/{id}")
    public String updateDamage(@PathVariable Long id, @ModelAttribute Damage damageDetails) {
        damageService.updateDamage(id, damageDetails);
        return "redirect:/damages";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Damage> damage = damageService.getDamageById(id);
        if (damage.isPresent()) {
            model.addAttribute("damage", damage.get());
            return "damages/details";
        }
        return "redirect:/damages";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDamage(@PathVariable Long id) {
        return damageService.deleteDamage(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
