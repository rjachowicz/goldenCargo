package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Report;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.ReportService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private static final String ALIAS = "r";
    private final ReportService reportService;
    private final UserService userService;
    private final GenericService genericService;

    public ReportController(ReportService reportService, UserService userService, GenericService genericService) {
        this.reportService = reportService;
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllReports(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "reportType") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {
        List<Report> reports = genericService.getFilteredAndSortedEntities(
                Report.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("reports", reports);
        model.addAttribute("report", new Report());
        model.addAttribute("users", userService.getAllUsers());
        return "reports/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("report", new Report());
        model.addAttribute("users", userService.getAllUsers());
        return "reports/create";
    }

    @PostMapping("/create")
    public String createReport(@ModelAttribute Report report) {
        reportService.createReport(report);
        return "redirect:/reports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Report report = reportService.getReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID: " + id));
        model.addAttribute("report", report);
        model.addAttribute("users", userService.getAllUsers());
        return "reports/edit :: editReportModal";
    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable Long id, @ModelAttribute Report reportDetails) {
        reportService.updateReport(id, reportDetails);
        return "redirect:/reports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Report report = reportService.getReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report ID: " + id));
        model.addAttribute("report", report);
        return "reports/details :: detailsReportModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportService.deleteReport(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
