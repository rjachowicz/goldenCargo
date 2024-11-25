package com.goldencargo.controller.web;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.service.DriverReportService;
import com.goldencargo.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/driverReports")
public class DriverReportController {

    private final DriverReportService driverReportService;
    private final DriverService driverService;

    public DriverReportController(DriverReportService driverReportService, DriverService driverService) {
        this.driverReportService = driverReportService;
        this.driverService = driverService;
    }

    @GetMapping
    public String getAllDriverReports(Model model) {
        List<DriverReport> driverReports = driverReportService.getAllDriverReports();
        model.addAttribute("driverReports", driverReports);
        return "driverReports/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("driverReport", new DriverReport());
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "driverReports/create";
    }

    @PostMapping("/create")
    public String createDriverReport(@ModelAttribute DriverReport driverReport) {
        driverReportService.createDriverReport(driverReport);
        return "redirect:/driverReports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<DriverReport> driverReport = driverReportService.getDriverReportById(id);
        if (driverReport.isPresent()) {
            model.addAttribute("driverReport", driverReport.get());
            model.addAttribute("drivers", driverService.getAllDrivers());
            return "driverReports/edit";
        }
        return "redirect:/driverReports";
    }

    @PostMapping("/update/{id}")
    public String updateDriverReport(@PathVariable Long id, @ModelAttribute DriverReport driverReportDetails) {
        driverReportService.updateDriverReport(id, driverReportDetails);
        return "redirect:/driverReports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<DriverReport> driverReport = driverReportService.getDriverReportById(id);
        if (driverReport.isPresent()) {
            model.addAttribute("driverReport", driverReport.get());
            return "driverReports/details";
        }
        return "redirect:/driverReports";
    }

    @PostMapping("/delete/{id}")
    public String deleteDriverReport(@PathVariable Long id) {
        driverReportService.deleteDriverReport(id);
        return "redirect:/driverReports";
    }
}
