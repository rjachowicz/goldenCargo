package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Report;
import com.goldencargo.service.ReportService;
import com.goldencargo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllReports(Model model) {
        List<Report> reports = reportService.getAllReports();
        model.addAttribute("reports", reports);
        return "reports/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("report", new Report());
        model.addAttribute("users", userService.getAllUsers());
        return "reports/create";
    }

    @PostMapping("/create")
    public String createReport(Report report) {
        reportService.createReport(report);
        return "redirect:/reports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Report> report = reportService.getReportById(id);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            model.addAttribute("users", userService.getAllUsers());
            return "reports/edit";
        }
        return "redirect:/reports";
    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable Long id, Report reportDetails) {
        reportService.updateReport(id, reportDetails);
        return "redirect:/reports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Report> report = reportService.getReportById(id);
        report.ifPresent(value -> model.addAttribute("report", value));
        return "reports/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return "redirect:/reports";
    }
}
