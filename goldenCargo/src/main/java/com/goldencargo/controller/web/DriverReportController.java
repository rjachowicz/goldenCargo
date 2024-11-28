package com.goldencargo.controller.web;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.service.DriverReportService;
import com.goldencargo.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/driver-reports")
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
        return "driver-reports/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("driverReport", new DriverReport());
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "driver-reports/create";
    }

    @PostMapping("/create")
    public String createDriverReport(@ModelAttribute DriverReport driverReport) {
        driverReportService.createDriverReport(driverReport);
        return "redirect:/driver-reports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<DriverReport> driverReport = driverReportService.getDriverReportById(id);
        if (driverReport.isPresent()) {
            model.addAttribute("driverReport", driverReport.get());
            model.addAttribute("drivers", driverService.getAllDrivers());
            return "driver-reports/edit";
        }
        return "redirect:/driver-reports";
    }

    @PostMapping("/update/{id}")
    public String updateDriverReport(@PathVariable Long id, @ModelAttribute DriverReport driverReportDetails) {
        driverReportService.updateDriverReport(id, driverReportDetails);
        return "redirect:/driver-reports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<DriverReport> driverReport = driverReportService.getDriverReportById(id);
        if (driverReport.isPresent()) {
            model.addAttribute("driverReport", driverReport.get());
            return "driver-reports/details";
        }
        return "redirect:/driver-reports";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriverReport(@PathVariable Long id) {
        return driverReportService.deleteDriverReport(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
