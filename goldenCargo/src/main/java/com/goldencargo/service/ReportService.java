package com.goldencargo.service;

import com.goldencargo.model.entities.Report;
import com.goldencargo.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findByIsDeletedFalse();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public Optional<Report> updateReport(Long id, Report reportDetails) {
        return reportRepository.findById(id).map(report -> {
            report.setReportType(reportDetails.getReportType());
            report.setDateGenerated(reportDetails.getDateGenerated());
            report.setGeneratedBy(reportDetails.getGeneratedBy());
            report.setContent(reportDetails.getContent());
            report.setUpdatedAt(new java.util.Date());
            return reportRepository.save(report);
        });
    }

    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
