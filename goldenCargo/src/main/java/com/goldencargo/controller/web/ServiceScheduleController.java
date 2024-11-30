package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.ServiceSchedule;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.ServiceScheduleService;
import com.goldencargo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/service-schedules")
public class ServiceScheduleController {

    private final ServiceScheduleService serviceScheduleService;
    private final VehicleService vehicleService;
    private final GenericService genericService;

    private static final String ALIAS = "s";

    public ServiceScheduleController(ServiceScheduleService serviceScheduleService, VehicleService vehicleService, GenericService genericService) {
        this.serviceScheduleService = serviceScheduleService;
        this.vehicleService = vehicleService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllSchedules(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "scheduledDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<ServiceSchedule> schedules = genericService.getFilteredAndSortedEntities(
                ServiceSchedule.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("serviceSchedules", schedules);
        model.addAttribute("serviceSchedule", new ServiceSchedule());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "service-schedules/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("serviceSchedule", new ServiceSchedule());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("statuses", Status.values());
        return "service-schedules/create";
    }

    @PostMapping("/create")
    public String createSchedule(@ModelAttribute ServiceSchedule serviceSchedule) {
        serviceScheduleService.createSchedule(serviceSchedule);
        return "redirect:/service-schedules";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ServiceSchedule> schedule = serviceScheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            model.addAttribute("serviceSchedule", schedule.get());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            model.addAttribute("statuses", Status.values());
            return "service-schedules/edit :: editServiceScheduleModal";
        }
        return "redirect:/service-schedules";
    }

    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable Long id, @ModelAttribute ServiceSchedule scheduleDetails) {
        serviceScheduleService.updateSchedule(id, scheduleDetails);
        return "redirect:/service-schedules";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ServiceSchedule> schedule = serviceScheduleService.getScheduleById(id);
        schedule.ifPresent(value -> model.addAttribute("serviceSchedule", value));
        return "service-schedules/details :: detailsServiceScheduleModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        return serviceScheduleService.deleteSchedule(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
