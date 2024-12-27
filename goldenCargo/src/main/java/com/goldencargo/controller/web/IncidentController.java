package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/incidents")
public class IncidentController {

    private static final String ALIAS = "i";
    private final IncidentService incidentService;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final UserService userService;
    private final GenericService genericService;

    public IncidentController(IncidentService incidentService,
                              VehicleService vehicleService,
                              DriverService driverService,
                              UserService userService,
                              GenericService genericService) {
        this.incidentService = incidentService;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllIncidents(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "date") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Incident> incidents = genericService.getFilteredAndSortedEntities(
                Incident.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("incidents", incidents);
        model.addAttribute("incident", new Incident());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterValue", filterValue);

        return "incidents/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("incident", new Incident());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", Status.values());
        return "incidents/create";
    }

    @PostMapping("/create")
    public String createIncident(@ModelAttribute Incident incident) {
        incidentService.createIncident(incident);
        return "redirect:/incidents";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid incident ID: " + id));
        model.addAttribute("incident", incident);
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", Status.values());
        return "incidents/edit";
    }

    @PostMapping("/update/{id}")
    public String updateIncident(@PathVariable Long id, @ModelAttribute Incident incidentDetails) {
        incidentService.updateIncident(id, incidentDetails);
        return "redirect:/incidents";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid incident ID: " + id));
        model.addAttribute("incident", incident);
        return "incidents/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        return incidentService.deleteIncident(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
