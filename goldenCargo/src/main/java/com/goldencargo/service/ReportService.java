package com.goldencargo.service;

import com.goldencargo.model.dto.web.InvoiceDTO;
import com.goldencargo.model.dto.web.ReportDataDTO;
import com.goldencargo.model.entities.Report;
import com.goldencargo.repository.ReportRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public ReportDataDTO generateReport(Long transportId) {
        String query = """
                         SELECT t.transport_id,
                                t.actual_departure,
                                t.actual_arrival,
                                t.notes,
                                t.created_at   AS transport_created_at,
                                t.updated_at   AS transport_updated_at,
                                t2.transport_order_name,
                                t2.scheduled_departure,
                                t2.scheduled_arrival,
                                t2.created_at  AS transport_order_created_at,
                                t2.updated_at  AS transport_order_updated_at,
                                d.license_number,
                                d.license_category,
                                d.hire_date,
                                d.date_of_birth,
                                d.medical_certificate_expiry,
                                v.make,
                                v.model,
                                v.registration_number,
                                v.capacity,
                                v.mileage,
                                v.year         AS vehicle_year,
                                v.last_service_date,
                                v.next_service_due,
                                v.purchase_date,
                                vt.name        AS vehicle_type_name,
                                vt.maximum_load,
                                vt.special_features,
                                vt.dimensions,
                                vt.description AS vehicle_type_description,
                                l1.name        AS start_location_name,
                                l1.address     AS start_address,
                                l1.city        AS start_city,
                                l1.state       AS start_state,
                                l1.country     AS start_country,
                                l1.postal_code AS start_postal_code,
                                l1.latitude    AS start_latitude,
                                l1.longitude   AS start_longitude,
                                l2.name        AS end_location_name,
                                l2.address     AS end_address,
                                l2.city        AS end_city,
                                l2.state       AS end_state,
                                l2.country     AS end_country,
                                l2.postal_code AS end_postal_code,
                                l2.latitude    AS end_latitude,
                                l2.longitude   AS end_longitude,
                                u.first_name,
                                u.last_name,
                                u.phone_number,
                                u.email,
                                co.client_order_id,
                                co.order_date AS client_order_date,
                                co.payment_status AS client_order_payment_status,
                                co.total_amount AS client_order_total_amount,
                                c.client_id,
                                c.name AS client_name,
                                c.nip AS client_nip,
                                c.phone AS client_phone,
                                c.email AS client_email,
                                c.contact_person AS client_contact_person,
                                c.address AS client_address
                         FROM transports t
                                  JOIN transport_orders t2 ON t.transport_order_id = t2.transport_order_id
                                  LEFT JOIN drivers d ON t2.assigned_driver_id = d.driver_id
                                  LEFT JOIN vehicles v ON t2.assigned_vehicle_id = v.vehicle_id
                                  JOIN vehicle_types vt ON v.vehicle_type_id = vt.vehicle_type_id
                                  JOIN locations l1 ON t2.start_location_id = l1.location_id
                                  JOIN locations l2 ON t2.end_location_id = l2.location_id
                                  JOIN users u ON d.user_id = u.user_id
                                  JOIN transport_order_client_orders toco ON t2.transport_order_id = toco.transport_order_id
                                  JOIN client_orders co ON toco.client_order_id = co.client_order_id
                                  JOIN clients c ON co.client_id = c.client_id
                         WHERE t.transport_id = ?;
                """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(query, transportId);
        return mapToReportData(results);
    }

    public InvoiceDTO generateInvoice(Long clientOrderId, Long clientId) {
        String query = """
                select ci.invoice_id,
                       ci.created_at     AS invoice_created_at,
                       ci.date_issued,
                       ci.due_date,
                       ci.invoice_number,
                       ci.payment_status AS invoice_payment_status,
                       ci.total_amount   AS invoice_total_amount,
                       c.client_id,
                       c.name            AS client_name,
                       c.address         AS client_address,
                       c.contact_person,
                       c.email           AS client_email,
                       c.nip             AS client_nip,
                       c.phone           AS client_phone,
                       co.client_order_id,
                       co.order_date     AS client_order_date,
                       co.total_amount   AS client_order_total_amount,
                       tor.transport_order_id,
                       tor.transport_order_name,
                       tor.scheduled_departure,
                       tor.scheduled_arrival,
                       t.transport_id,
                       t.actual_departure,
                       t.actual_arrival,
                       t.notes           AS transport_notes
                from client_invoices ci
                         join clients c on ci.client_id = c.client_id
                         join client_orders co on ci.client_order_id = co.client_order_id
                         join transport_order_client_orders toco on co.client_order_id = toco.client_order_id
                         join transport_orders tor on toco.transport_order_id = tor.transport_order_id
                         join transports t on tor.transport_order_id = t.transport_order_id
                where ci.client_order_id = ?
                  and ci.client_id = ?;
                """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(query, clientOrderId, clientId);
        return mapToInvoiceData(results);
    }

    private InvoiceDTO mapToInvoiceData(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> map = results.getFirst();

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setInvoiceId((Long) map.get("invoice_id"));

        invoiceDTO.setInvoiceCreatedAt(((Timestamp) map.get("invoice_created_at")).toLocalDateTime());

        invoiceDTO.setDateIssued(((Timestamp) map.get("date_issued")).toLocalDateTime().toLocalDate());
        invoiceDTO.setDueDate(((Timestamp) map.get("due_date")).toLocalDateTime().toLocalDate());

        invoiceDTO.setInvoiceNumber((String) map.get("invoice_number"));
        invoiceDTO.setInvoicePaymentStatus((String) map.get("invoice_payment_status"));
        invoiceDTO.setInvoiceTotalAmount((Double) map.get("invoice_total_amount"));

        invoiceDTO.setClientId((Long) map.get("client_id"));
        invoiceDTO.setClientName((String) map.get("client_name"));
        invoiceDTO.setClientAddress((String) map.get("client_address"));
        invoiceDTO.setContactPerson((String) map.get("contact_person"));
        invoiceDTO.setClientEmail((String) map.get("client_email"));
        invoiceDTO.setClientNip((String) map.get("client_nip"));
        invoiceDTO.setClientPhone((String) map.get("client_phone"));

        invoiceDTO.setClientOrderId((Long) map.get("client_order_id"));

        invoiceDTO.setClientOrderDate(((Timestamp) map.get("client_order_date")).toLocalDateTime());
        invoiceDTO.setClientOrderTotalAmount((Double) map.get("client_order_total_amount"));

        invoiceDTO.setTransportOrderId((Long) map.get("transport_order_id"));
        invoiceDTO.setTransportOrderName((String) map.get("transport_order_name"));

        invoiceDTO.setScheduledDeparture(((Timestamp) map.get("scheduled_departure")).toLocalDateTime());
        invoiceDTO.setScheduledArrival(((Timestamp) map.get("scheduled_arrival")).toLocalDateTime());

        invoiceDTO.setTransportId((Long) map.get("transport_id"));
        invoiceDTO.setActualDeparture(((Timestamp) map.get("actual_departure")).toLocalDateTime());
        invoiceDTO.setActualArrival(((Timestamp) map.get("actual_arrival")).toLocalDateTime());
        invoiceDTO.setTransportNotes((String) map.get("transport_notes"));

        return invoiceDTO;
    }


    private ReportDataDTO mapToReportData(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> row = results.getFirst();
        ReportDataDTO report = new ReportDataDTO();

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

        report.setClientOrderId(asInteger(row.get("client_order_id")));
        report.setClientOrderDate(asString(row.get("client_order_date")));
        report.setClientOrderPaymentStatus(asString(row.get("client_order_payment_status")));
        report.setClientOrderTotalAmount(asString(row.get("client_order_total_amount")));

        report.setClientId(asInteger(row.get("client_id")));
        report.setClientName(asString(row.get("client_name")));
        report.setClientNip(asString(row.get("client_nip")));
        report.setClientPhone(asString(row.get("client_phone")));
        report.setClientEmail(asString(row.get("client_email")));
        report.setClientContactPerson(asString(row.get("client_contact_person")));
        report.setClientAddress(asString(row.get("client_address")));
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
