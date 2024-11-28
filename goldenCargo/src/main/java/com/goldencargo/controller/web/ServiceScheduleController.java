package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.ServiceSchedule;
import com.goldencargo.service.ServiceScheduleService;
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
@RequestMapping("/service-schedules")
public class ServiceScheduleController {

    private final ServiceScheduleService serviceScheduleService;
    private final VehicleService VehicleService;

    public ServiceScheduleController(ServiceScheduleService serviceScheduleService, VehicleService VehicleService) {
        this.serviceScheduleService = serviceScheduleService;
        this.VehicleService = VehicleService;
    }

    @GetMapping
    public String getAllSchedules(Model model) {
        List<ServiceSchedule> schedules = serviceScheduleService.getAllSchedules();
        model.addAttribute("serviceSchedules", schedules);
        return "service-schedules/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("serviceSchedule", new ServiceSchedule());
        model.addAttribute("vehicles", VehicleService.getAllVehicles());
        model.addAttribute("statuses", Status.values());
        return "service-schedules/create";
    }

    @PostMapping("/create")
    public String createSchedule(ServiceSchedule serviceSchedule) {
        serviceScheduleService.createSchedule(serviceSchedule);
        return "redirect:/service-schedules";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ServiceSchedule> schedule = serviceScheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            model.addAttribute("serviceSchedule", schedule.get());
            model.addAttribute("vehicles", VehicleService.getAllVehicles());
            model.addAttribute("statuses", Status.values());
            return "service-schedules/edit";
        }
        return "redirect:/service-schedules";
    }

    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable Long id, ServiceSchedule scheduleDetails) {
        serviceScheduleService.updateSchedule(id, scheduleDetails);
        return "redirect:/service-schedules";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ServiceSchedule> schedule = serviceScheduleService.getScheduleById(id);
        schedule.ifPresent(value -> model.addAttribute("serviceSchedule", value));
        return "service-schedules/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        serviceScheduleService.deleteSchedule(id);
        return "redirect:/service-schedules";
    }
}
