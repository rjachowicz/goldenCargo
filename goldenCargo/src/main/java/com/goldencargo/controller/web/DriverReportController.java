package com.goldencargo.controller.web;

import com.goldencargo.model.entities.DriverReport;
import com.goldencargo.service.DriverReportService;
import com.goldencargo.service.DriverService;
import com.goldencargo.service.GenericService;
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
    private final GenericService genericService;

    private static final String ALIAS = "dr";

    public DriverReportController(DriverReportService driverReportService, DriverService driverService, GenericService genericService) {
        this.driverReportService = driverReportService;
        this.driverService = driverService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllDriverReports(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "date") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "desc") String sortLogic,
            Model model) {

        List<DriverReport> driverReports = genericService.getFilteredAndSortedEntities(
                DriverReport.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("driverReports", driverReports);
        model.addAttribute("driverReport", new DriverReport());
        model.addAttribute("drivers", driverService.getAllDrivers());

        return "driver-reports/main";
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
            return "driver-reports/edit :: editDriverReportModal";
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
        driverReport.ifPresent(value -> model.addAttribute("driverReport", value));
        return "driver-reports/details :: detailsDriverReportModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriverReport(@PathVariable Long id) {
        boolean isDeleted = driverReportService.deleteDriverReport(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
