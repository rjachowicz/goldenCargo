package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.*;
import com.goldencargo.service.*;
import com.goldencargo.service.VehicleRepairsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final VehicleRepairsService vehicleRepairsService;
    private final DriverService driverService;
    private final UserService userService;

    public IncidentController(IncidentService incidentService,
                              VehicleRepairsService vehicleRepairsService,
                              DriverService driverService,
                              UserService userService) {
        this.incidentService = incidentService;
        this.vehicleRepairsService = vehicleRepairsService;
        this.driverService = driverService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllIncidents(Model model) {
        List<Incident> incidents = incidentService.getAllIncidents();
        model.addAttribute("incidents", incidents);
        return "incidents/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("incident", new Incident());
        model.addAttribute("vehicles", vehicleRepairsService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", Status.values());
        return "incidents/create";
    }

    @PostMapping("/create")
    public String createIncident(Incident incident) {
        incidentService.createIncident(incident);
        return "redirect:/incidents";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        if (incident.isPresent()) {
            model.addAttribute("incident", incident.get());
            model.addAttribute("vehicles", vehicleRepairsService.getAllVehicles());
            model.addAttribute("drivers", driverService.getAllDrivers());
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("statuses", Status.values());
            return "incidents/edit";
        }
        return "redirect:/incidents";
    }

    @PostMapping("/update/{id}")
    public String updateIncident(@PathVariable Long id, Incident incidentDetails) {
        incidentService.updateIncident(id, incidentDetails);
        return "redirect:/incidents";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        incident.ifPresent(value -> model.addAttribute("incident", value));
        return "incidents/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return "redirect:/incidents";
    }
}
