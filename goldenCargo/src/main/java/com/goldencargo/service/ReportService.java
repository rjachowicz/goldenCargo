package com.goldencargo.service;

import com.goldencargo.component.ReportData;
import com.goldencargo.model.entities.Report;
import com.goldencargo.repository.ReportRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository, JdbcTemplate jdbcTemplate) {
        this.reportRepository = reportRepository;
        this.jdbcTemplate = jdbcTemplate;
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

    public ReportData generateReport(Long transportId) {
        String query = """
                     SELECT
                         t.transport_id, t.actual_departure, t.actual_arrival, t.notes,
                         t.created_at AS transport_created_at, t.updated_at AS transport_updated_at,
                         t2.transport_order_name, t2.scheduled_departure, t2.scheduled_arrival,
                         t2.created_at AS transport_order_created_at, t2.updated_at AS transport_order_updated_at,
                         d.license_number, d.license_category, d.hire_date, d.date_of_birth, d.medical_certificate_expiry,
                         v.make, v.model, v.registration_number, v.capacity, v.mileage, v.year AS vehicle_year,
                         v.last_service_date, v.next_service_due, v.purchase_date,
                         vt.name AS vehicle_type_name, vt.maximum_load, vt.special_features, vt.dimensions, vt.description AS vehicle_type_description,
                         l1.name AS start_location_name, l1.address AS start_address, l1.city AS start_city,
                         l1.state AS start_state, l1.country AS start_country, l1.postal_code AS start_postal_code,
                         l1.latitude AS start_latitude, l1.longitude AS start_longitude,
                         l2.name AS end_location_name, l2.address AS end_address, l2.city AS end_city,
                         l2.state AS end_state, l2.country AS end_country, l2.postal_code AS end_postal_code,
                         l2.latitude AS end_latitude, l2.longitude AS end_longitude,
                         u.first_name, u.last_name, u.phone_number, u.email
                     FROM transports t
                     JOIN transport_orders t2 ON t.transport_order_id = t2.transport_order_id
                     LEFT JOIN drivers d ON t2.assigned_driver_id = d.driver_id
                     LEFT JOIN vehicles v ON t2.assigned_vehicle_id = v.vehicle_id
                     JOIN vehicle_types vt ON v.vehicle_type_id = vt.vehicle_type_id
                     JOIN locations l1 ON t2.start_location_id = l1.location_id
                     JOIN locations l2 ON t2.end_location_id = l2.location_id
                     JOIN users u ON d.user_id = u.user_id
                     WHERE t.transport_id = ?;
                """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(query, transportId);
        return mapToReportData(results);
    }

    private ReportData mapToReportData(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> row = results.getFirst();
        ReportData report = new ReportData();

        report.setTransportId(asInteger(row.get("transport_id")));
        report.setActualDeparture(asString(row.get("actual_departure")));
        report.setActualArrival(asString(row.get("actual_arrival")));
        report.setNotes((String) row.get("notes"));
        report.setTransportCreatedAt(asString(row.get("transport_created_at")));
        report.setTransportUpdatedAt(asString(row.get("transport_updated_at")));

        report.setTransportOrderName((String) row.get("transport_order_name"));
        report.setScheduledDeparture(asString(row.get("scheduled_departure")));
        report.setScheduledArrival(asString(row.get("scheduled_arrival")));
        report.setTransportOrderCreatedAt(asString(row.get("transport_order_created_at")));
        report.setTransportOrderUpdatedAt(asString(row.get("transport_order_updated_at")));

        report.setLicenseNumber((String) row.get("license_number"));
        report.setLicenseCategory((String) row.get("license_category"));
        report.setHireDate(asString(row.get("hire_date")));
        report.setDateOfBirth(asString(row.get("date_of_birth")));
        report.setMedicalCertificateExpiry(asString(row.get("medical_certificate_expiry")));

        String firstName = asString(row.get("first_name"));
        String lastName = asString(row.get("last_name"));
        report.setDriverName(firstName + " " + lastName);
        report.setDriverPhone(asString(row.get("phone_number")));
        report.setDriverEmail(asString(row.get("email")));

        report.setMake((String) row.get("make"));
        report.setModel((String) row.get("model"));
        report.setRegistrationNumber((String) row.get("registration_number"));
        report.setCapacity(asDouble(row.get("capacity")));
        report.setMileage(asDouble(row.get("mileage")));
        report.setVehicleYear(asInteger(row.get("vehicle_year")));
        report.setLastServiceDate(asString(row.get("last_service_date")));
        report.setNextServiceDue(asString(row.get("next_service_due")));
        report.setPurchaseDate(asString(row.get("purchase_date")));

        report.setVehicleTypeName(asString(row.get("vehicle_type_name")));
        report.setMaximumLoad(asDouble(row.get("maximum_load")));
        report.setSpecialFeatures(asString(row.get("special_features")));
        report.setDimensions(asString(row.get("dimensions")));
        report.setVehicleTypeDescription(asString(row.get("vehicle_type_description")));

        report.setStartLocationName(asString(row.get("start_location_name")));
        report.setStartAddress(asString(row.get("start_address")));
        report.setStartCity(asString(row.get("start_city")));
        report.setStartState(asString(row.get("start_state")));
        report.setStartCountry(asString(row.get("start_country")));
        report.setStartPostalCode(asString(row.get("start_postal_code")));
        report.setStartLatitude(asDouble(row.get("start_latitude")));
        report.setStartLongitude(asDouble(row.get("start_longitude")));

        report.setEndLocationName(asString(row.get("end_location_name")));
        report.setEndAddress(asString(row.get("end_address")));
        report.setEndCity(asString(row.get("end_city")));
        report.setEndState(asString(row.get("end_state")));
        report.setEndCountry(asString(row.get("end_country")));
        report.setEndPostalCode(asString(row.get("end_postal_code")));
        report.setEndLatitude(asDouble(row.get("end_latitude")));
        report.setEndLongitude(asDouble(row.get("end_longitude")));

        return report;
    }

    private String asString(Object value) {
        return (value != null) ? value.toString() : null;
    }

    private Double asDouble(Object value) {
        return (value instanceof Number) ? ((Number) value).doubleValue() : null;
    }

    private Integer asInteger(Object value) {
        return (value instanceof Number) ? ((Number) value).intValue() : null;
    }
}
