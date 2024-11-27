package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Logistics;
import com.goldencargo.service.LogisticsService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/logistics")
public class LogisticsController {

    private final LogisticsService logisticsService;
    private final UserService userService;

    public LogisticsController(LogisticsService logisticsService, UserService userService) {
        this.logisticsService = logisticsService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllLogistics(Model model) {
        List<Logistics> logistics = logisticsService.getAllLogistics();
        model.addAttribute("logistics", logistics);
        return "logistics/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("logistics", new Logistics());
        model.addAttribute("users", userService.getUsersNotAssignedAsLogistic());
        return "logistics/create";
    }

    @PostMapping("/create")
    public String createLogistic(Logistics logistics) {
        logisticsService.createLogistics(logistics);
        return "redirect:/logistics";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Logistics> logistics = logisticsService.getLogisticById(id);
        if (logistics.isPresent()) {
            model.addAttribute("logistics", logistics.get());
            model.addAttribute("users", userService.getAllUsers());
            return "logistics/edit";
        }
        return "redirect:/logistics";
    }

    @PostMapping("/update/{id}")
    public String updateLogistic(@PathVariable Long id, Logistics logisticDetails) {
        logisticsService.updateLogistics(id, logisticDetails);
        return "redirect:/logistics";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Logistics> logistics = logisticsService.getLogisticById(id);
        logistics.ifPresent(value -> model.addAttribute("logistics", value));
        return "logistics/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLogistic(@PathVariable Long id) {
        return logisticsService.deleteLogistics(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
