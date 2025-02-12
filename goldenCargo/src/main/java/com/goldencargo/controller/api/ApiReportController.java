package com.goldencargo.controller.api;

import com.goldencargo.model.dto.api.ReportDTO;
import com.goldencargo.model.entities.Report;
import com.goldencargo.model.entities.User;
import com.goldencargo.service.ReportService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ApiReportController {

    private final ReportService reportService;
    private final UserService userService;

    public ApiReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<ReportDTO> getReports(@AuthenticationPrincipal UserDetails userDetails) {
        User userByUsername = getUser(userDetails);
        return reportService.getReportsByUser(userByUsername.getUserId());
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createReport(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody Report report) {
        try {
            User userByUsername = getUser(userDetails);
            report.setGeneratedBy(userByUsername);
            report.setDateGenerated(new Date());
            reportService.createReport(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Report has been created"));
    }

    private User getUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return userService.findUserByUsername(userName);
    }
}
