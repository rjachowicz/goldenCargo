package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Logistics;
import com.goldencargo.service.GenericService;
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
    private final GenericService genericService;

    public LogisticsController(LogisticsService logisticsService, UserService userService, GenericService genericService) {
        this.logisticsService = logisticsService;
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllLogistics(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "user") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Logistics> logistics = genericService.getFilteredAndSortedEntities(
                Logistics.class,
                "l",
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("logisticsList", logistics);
        model.addAttribute("logistics", new Logistics());
        model.addAttribute("users", userService.getUsersNotAssignedAsLogistic());
        model.addAttribute("statuses", Status.values());
        return "logistics/main";
    }

    @PostMapping("/create")
    public String createLogistic(Logistics logistics) {
        logisticsService.createLogistics(logistics);
        return "redirect:/logistics";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
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
    public String showDetails(@PathVariable("id") Long id, Model model) {
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
