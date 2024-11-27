package com.goldencargo.controller.web;

import com.goldencargo.model.entities.VehicleRepairs;
import com.goldencargo.service.VehicleRepairsService;
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
@RequestMapping("/vehicle-repairs")
public class VehicleRepairsController {

    private final VehicleRepairsService vehicleRepairsService;
    private final VehicleService vehicleService;

    public VehicleRepairsController(VehicleRepairsService vehicleRepairsService, VehicleService vehicleService) {
        this.vehicleRepairsService = vehicleRepairsService;
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String getAllRepairs(Model model) {
        List<VehicleRepairs> repairs = vehicleRepairsService.getAllRepairs();
        model.addAttribute("vehicleRepairs", repairs);
        return "vehicle-repairs/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicleRepair", new VehicleRepairs());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "vehicle-repairs/create";
    }

    @PostMapping("/create")
    public String createRepair(VehicleRepairs repair) {
        vehicleRepairsService.createRepair(repair);
        return "redirect:/vehicle-repairs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<VehicleRepairs> repair = vehicleRepairsService.getRepairById(id);
        if (repair.isPresent()) {
            model.addAttribute("vehicleRepair", repair.get());
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            return "vehicle-repairs/edit";
        }
        return "redirect:/vehicle-repairs";
    }

    @PostMapping("/update/{id}")
    public String updateRepair(@PathVariable Long id, VehicleRepairs repairDetails) {
        vehicleRepairsService.updateRepair(id, repairDetails);
        return "redirect:/vehicle-repairs";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<VehicleRepairs> repair = vehicleRepairsService.getRepairById(id);
        repair.ifPresent(value -> model.addAttribute("vehicleRepair", value));
        return "vehicle-repairs/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteRepair(@PathVariable Long id) {
        vehicleRepairsService.deleteRepair(id);
        return "redirect:/vehicle-repairs";
    }
}
