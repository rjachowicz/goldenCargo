package com.goldencargo.service;

import com.goldencargo.model.dto.api.TransportOrderAdvancedDetailsDTO;
import com.goldencargo.model.dto.api.TransportOrderWithTransportDTO;
import com.goldencargo.model.dto.web.InvoiceDTO;
import com.goldencargo.model.dto.web.ReportDataDTO;
import com.goldencargo.model.entities.Report;
import com.goldencargo.repository.ReportRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {

    public static final String TRANSPORT_ORDER_DATA_QUERY = """
            SELECT t.transport_order_id,
                   t.created_at      AS transport_created_at,
                   t.updated_at      AS transport_updated_at,
                   t.transport_order_name,
                   t.scheduled_departure,
                   t.scheduled_arrival,
                   t.created_at      AS transport_order_created_at,
                   t.updated_at      AS transport_order_updated_at,
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
                   v.year            AS vehicle_year,
                   v.last_service_date,
                   v.next_service_due,
                   v.purchase_date,
                   vt.name           AS vehicle_type_name,
                   vt.maximum_load,
                   vt.special_features,
                   vt.dimensions,
                   vt.description    AS vehicle_type_description,
                   l1.name           AS start_location_name,
                   l1.address        AS start_address,
                   l1.city           AS start_city,
                   l1.state          AS start_state,
                   l1.country        AS start_country,
                   l1.postal_code    AS start_postal_code,
                   l1.latitude       AS start_latitude,
                   l1.longitude      AS start_longitude,
                   l2.name           AS end_location_name,
                   l2.address        AS end_address,
                   l2.city           AS end_city,
                   l2.state          AS end_state,
                   l2.country        AS end_country,
                   l2.postal_code    AS end_postal_code,
                   l2.latitude       AS end_latitude,
                   l2.longitude      AS end_longitude,
                   u.first_name,
                   u.last_name,
                   u.phone_number,
                   u.email,
                   co.client_order_id,
                   co.order_date     AS client_order_date,
                   co.payment_status AS client_order_payment_status,
                   co.total_amount   AS client_order_total_amount,
                   c.client_id,
                   c.name            AS client_name,
                   c.nip             AS client_nip,
                   c.phone           AS client_phone,
                   c.email           AS client_email,
                   c.contact_person  AS client_contact_person,
                   c.address         AS client_address
            FROM transport_orders t
                     LEFT JOIN drivers d ON t.assigned_driver_id = d.driver_id
                     LEFT JOIN vehicles v ON t.assigned_vehicle_id = v.vehicle_id
                     JOIN vehicle_types vt ON v.vehicle_type_id = vt.vehicle_type_id
                     JOIN locations l1 ON t.start_location_id = l1.location_id
                     JOIN locations l2 ON t.end_location_id = l2.location_id
                     JOIN users u ON d.user_id = u.user_id
                     JOIN transport_order_client_orders toco ON t.transport_order_id = toco.transport_order_id
                     JOIN client_orders co ON toco.client_order_id = co.client_order_id
                     JOIN clients c ON co.client_id = c.client_id
            WHERE (:userId IS NULL OR t.assigned_driver_id = :userId)
              AND (:status IS NULL OR t.status IN (:status))
            ORDER BY t.created_at
            LIMIT 1;
            """;

    public static final String TRANSPORT_ORDER_WITH_TRANSPORT_DATA_QUERY = """
                 SELECT t.transport_order_id,
                        t.created_at      AS transport_created_at,
                        t.updated_at      AS transport_updated_at,
                        t.transport_order_name,
                        t.scheduled_departure,
                        t.scheduled_arrival,
                        t.created_at      AS transport_order_created_at,
                        t.updated_at      AS transport_order_updated_at,
                        tr.transport_id,
                        tr.actual_departure,
                        tr.actual_arrival,
                        tr.notes,
                        tr.status         AS transport_status,
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
                        v.year            AS vehicle_year,
                        v.last_service_date,
                        v.next_service_due,
                        v.purchase_date,
                        vt.name           AS vehicle_type_name,
                        vt.maximum_load,
                        vt.special_features,
                        vt.dimensions,
                        vt.description    AS vehicle_type_description,
                        l1.name           AS start_location_name,
                        l1.address        AS start_address,
                        l1.city           AS start_city,
                        l1.state          AS start_state,
                        l1.country        AS start_country,
                        l1.postal_code    AS start_postal_code,
                        l1.latitude       AS start_latitude,
                        l1.longitude      AS start_longitude,
                        l2.name           AS end_location_name,
                        l2.address        AS end_address,
                        l2.city           AS end_city,
                        l2.state          AS end_state,
                        l2.country        AS end_country,
                        l2.postal_code    AS end_postal_code,
                        l2.latitude       AS end_latitude,
                        l2.longitude      AS end_longitude,
                        u.first_name,
                        u.last_name,
                        u.phone_number,
                        u.email,
                        co.client_order_id,
                        co.order_date     AS client_order_date,
                        co.payment_status AS client_order_payment_status,
                        co.total_amount   AS client_order_total_amount,
                        c.client_id,
                        c.name            AS client_name,
                        c.nip             AS client_nip,
                        c.phone           AS client_phone,
                        c.email           AS client_email,
                        c.contact_person  AS client_contact_person,
                        c.address         AS client_address
                 FROM transport_orders t
                          LEFT JOIN drivers d ON t.assigned_driver_id = d.driver_id
                          LEFT JOIN vehicles v ON t.assigned_vehicle_id = v.vehicle_id
                          JOIN vehicle_types vt ON v.vehicle_type_id = vt.vehicle_type_id
                          JOIN locations l1 ON t.start_location_id = l1.location_id
                          JOIN locations l2 ON t.end_location_id = l2.location_id
                          JOIN users u ON d.user_id = u.user_id
                          JOIN transport_order_client_orders toco ON t.transport_order_id = toco.transport_order_id
                          JOIN client_orders co ON toco.client_order_id = co.client_order_id
                          JOIN clients c ON co.client_id = c.client_id
                          JOIN transports tr ON tr.transport_order_id = t.transport_order_id
                 WHERE (:userId IS NULL OR t.assigned_driver_id = :userId)
                   AND (:status IS NULL OR tr.status IN (:status))
                 ORDER BY t.created_at
                 LIMIT 1;
            """;

    public static final String GENERATE_REPORT_DATA_QUERY = """
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

    public static final String GENERATE_INVOICE_QUERY = """
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

    private final JdbcTemplate jdbcTemplate;
    private final ReportRepository reportRepository;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ReportService(ReportRepository reportRepository, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.reportRepository = reportRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
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
        List<Map<String, Object>> results = jdbcTemplate.queryForList(GENERATE_REPORT_DATA_QUERY, transportId);
        return mapToReportData(results);
    }

    public InvoiceDTO generateInvoice(Long clientOrderId, Long clientId) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(GENERATE_INVOICE_QUERY, clientOrderId, clientId);
        return mapToInvoiceData(results);
    }

    public TransportOrderAdvancedDetailsDTO generateTransportOrderData(Long userId, String status) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("status", status);
        List<Map<String, Object>> results = namedJdbcTemplate.queryForList(TRANSPORT_ORDER_DATA_QUERY, parameters);
        return mapToTransportOrderDetailsDTO(results);
    }

    public TransportOrderWithTransportDTO generateTransportOrderDataWithTransport(Long userId, String status) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("status", status);
        List<Map<String, Object>> results = namedJdbcTemplate.queryForList(TRANSPORT_ORDER_WITH_TRANSPORT_DATA_QUERY, parameters);
        return mapToTransportOrderWithTransportDTO(results);
    }

    private InvoiceDTO mapToInvoiceData(List<Map<String, Object>> results) {
        Map<String, Object> row = getFirstRow(results);
        if (row == null) {
            return null;
        }
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId((Long) row.get("invoice_id"));
        invoiceDTO.setInvoiceCreatedAt(((Timestamp) row.get("invoice_created_at")).toLocalDateTime());
        invoiceDTO.setDateIssued(((Timestamp) row.get("date_issued")).toLocalDateTime().toLocalDate());
        invoiceDTO.setDueDate(((Timestamp) row.get("due_date")).toLocalDateTime().toLocalDate());
        invoiceDTO.setInvoiceNumber((String) row.get("invoice_number"));
        invoiceDTO.setInvoicePaymentStatus((String) row.get("invoice_payment_status"));
        invoiceDTO.setInvoiceTotalAmount((Double) row.get("invoice_total_amount"));
        invoiceDTO.setClientId((Long) row.get("client_id"));
        invoiceDTO.setClientName((String) row.get("client_name"));
        invoiceDTO.setClientAddress((String) row.get("client_address"));
        invoiceDTO.setContactPerson((String) row.get("contact_person"));
        invoiceDTO.setClientEmail((String) row.get("client_email"));
        invoiceDTO.setClientNip((String) row.get("client_nip"));
        invoiceDTO.setClientPhone((String) row.get("client_phone"));
        invoiceDTO.setClientOrderId((Long) row.get("client_order_id"));
        invoiceDTO.setClientOrderDate(((Timestamp) row.get("client_order_date")).toLocalDateTime());
        invoiceDTO.setClientOrderTotalAmount((Double) row.get("client_order_total_amount"));
        invoiceDTO.setTransportOrderId((Long) row.get("transport_order_id"));
        invoiceDTO.setTransportOrderName((String) row.get("transport_order_name"));
        invoiceDTO.setScheduledDeparture(((Timestamp) row.get("scheduled_departure")).toLocalDateTime());
        invoiceDTO.setScheduledArrival(((Timestamp) row.get("scheduled_arrival")).toLocalDateTime());
        invoiceDTO.setTransportId((Long) row.get("transport_id"));
        invoiceDTO.setActualDeparture(((Timestamp) row.get("actual_departure")).toLocalDateTime());
        invoiceDTO.setActualArrival(((Timestamp) row.get("actual_arrival")).toLocalDateTime());
        invoiceDTO.setTransportNotes((String) row.get("transport_notes"));
        return invoiceDTO;
    }

    private ReportDataDTO mapToReportData(List<Map<String, Object>> results) {
        Map<String, Object> row = getFirstRow(results);
        if (row == null) {
            return null;
        }
        ReportDataDTO report = new ReportDataDTO();
        report.setTransportId(asInteger(row.get("transport_id")));
        report.setActualDeparture(asString(row.get("actual_departure")));
        report.setActualArrival(asString(row.get("actual_arrival")));
        report.setNotes(asString(row.get("notes")));
        report.setTransportCreatedAt(asString(row.get("transport_created_at")));
        report.setTransportUpdatedAt(asString(row.get("transport_updated_at")));
        report.setTransportOrderName(asString(row.get("transport_order_name")));
        report.setScheduledDeparture(asString(row.get("scheduled_departure")));
        report.setScheduledArrival(asString(row.get("scheduled_arrival")));
        report.setTransportOrderCreatedAt(asString(row.get("transport_order_created_at")));
        report.setTransportOrderUpdatedAt(asString(row.get("transport_order_updated_at")));
        report.setLicenseNumber(asString(row.get("license_number")));
        report.setLicenseCategory(asString(row.get("license_category")));
        report.setHireDate(asString(row.get("hire_date")));
        report.setDateOfBirth(asString(row.get("date_of_birth")));
        report.setMedicalCertificateExpiry(asString(row.get("medical_certificate_expiry")));
        String firstName = asString(row.get("first_name"));
        String lastName = asString(row.get("last_name"));
        report.setDriverName(firstName + " " + lastName);
        report.setDriverPhone(asString(row.get("phone_number")));
        report.setDriverEmail(asString(row.get("email")));
        report.setMake(asString(row.get("make")));
        report.setModel(asString(row.get("model")));
        report.setRegistrationNumber(asString(row.get("registration_number")));
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

    private TransportOrderAdvancedDetailsDTO mapToTransportOrderDetailsDTO(List<Map<String, Object>> results) {
        Map<String, Object> row = getFirstRow(results);
        if (row == null) {
            return null;
        }
        TransportOrderAdvancedDetailsDTO dto = new TransportOrderAdvancedDetailsDTO();
        populateCommonTransportOrder(dto, row);
        return dto;
    }

    private TransportOrderWithTransportDTO mapToTransportOrderWithTransportDTO(List<Map<String, Object>> results) {
        Map<String, Object> row = getFirstRow(results);
        if (row == null) {
            return null;
        }
        TransportOrderWithTransportDTO dto = new TransportOrderWithTransportDTO();
        populateCommonTransportOrder(dto, row);
        dto.setTransportId(asInteger(row.get("transport_id")));
        dto.setActualDeparture(asString(row.get("actual_departure")));
        dto.setActualArrival(asString(row.get("actual_arrival")));
        dto.setNotes(asString(row.get("notes")));
        dto.setTransportStatus(asString(row.get("transport_status")));
        return dto;
    }

    private void populateCommonTransportOrder(TransportOrderAdvancedDetailsDTO dto, Map<String, Object> row) {
        dto.setTransportOrderId(asInteger(row.get("transport_order_id")));
        dto.setTransportCreatedAt(asString(row.get("transport_created_at")));
        dto.setTransportUpdatedAt(asString(row.get("transport_updated_at")));
        dto.setTransportOrderName(asString(row.get("transport_order_name")));
        dto.setScheduledDeparture(asString(row.get("scheduled_departure")));
        dto.setScheduledArrival(asString(row.get("scheduled_arrival")));
        dto.setTransportOrderCreatedAt(asString(row.get("transport_order_created_at")));
        dto.setTransportOrderUpdatedAt(asString(row.get("transport_order_updated_at")));
        dto.setLicenseNumber(asString(row.get("license_number")));
        dto.setLicenseCategory(asString(row.get("license_category")));
        dto.setHireDate(asString(row.get("hire_date")));
        dto.setDateOfBirth(asString(row.get("date_of_birth")));
        dto.setMedicalCertificateExpiry(asString(row.get("medical_certificate_expiry")));
        dto.setFirstName(asString(row.get("first_name")));
        dto.setLastName(asString(row.get("last_name")));
        dto.setPhoneNumber(asString(row.get("phone_number")));
        dto.setEmail(asString(row.get("email")));
        dto.setMake(asString(row.get("make")));
        dto.setModel(asString(row.get("model")));
        dto.setRegistrationNumber(asString(row.get("registration_number")));
        dto.setCapacity(asDouble(row.get("capacity")));
        dto.setMileage(asInteger(row.get("mileage")));
        dto.setVehicleYear(asInteger(row.get("vehicle_year")));
        dto.setLastServiceDate(asString(row.get("last_service_date")));
        dto.setNextServiceDue(asString(row.get("next_service_due")));
        dto.setPurchaseDate(asString(row.get("purchase_date")));
        dto.setVehicleTypeName(asString(row.get("vehicle_type_name")));
        dto.setMaximumLoad(asDouble(row.get("maximum_load")));
        dto.setSpecialFeatures(asString(row.get("special_features")));
        dto.setDimensions(asString(row.get("dimensions")));
        dto.setVehicleTypeDescription(asString(row.get("vehicle_type_description")));
        dto.setStartLocationName(asString(row.get("start_location_name")));
        dto.setStartAddress(asString(row.get("start_address")));
        dto.setStartCity(asString(row.get("start_city")));
        dto.setStartState(asString(row.get("start_state")));
        dto.setStartCountry(asString(row.get("start_country")));
        dto.setStartPostalCode(asString(row.get("start_postal_code")));
        dto.setStartLatitude(asDouble(row.get("start_latitude")));
        dto.setStartLongitude(asDouble(row.get("start_longitude")));
        dto.setEndLocationName(asString(row.get("end_location_name")));
        dto.setEndAddress(asString(row.get("end_address")));
        dto.setEndCity(asString(row.get("end_city")));
        dto.setEndState(asString(row.get("end_state")));
        dto.setEndCountry(asString(row.get("end_country")));
        dto.setEndPostalCode(asString(row.get("end_postal_code")));
        dto.setEndLatitude(asDouble(row.get("end_latitude")));
        dto.setEndLongitude(asDouble(row.get("end_longitude")));
        dto.setClientOrderId(asInteger(row.get("client_order_id")));
        dto.setClientOrderDate(asString(row.get("client_order_date")));
        dto.setClientOrderPaymentStatus(asString(row.get("client_order_payment_status")));
        dto.setClientOrderTotalAmount(asDouble(row.get("client_order_total_amount")));
        dto.setClientId(asInteger(row.get("client_id")));
        dto.setClientName(asString(row.get("client_name")));
        dto.setClientNip(asString(row.get("client_nip")));
        dto.setClientPhone(asString(row.get("client_phone")));
        dto.setClientEmail(asString(row.get("client_email")));
        dto.setClientContactPerson(asString(row.get("client_contact_person")));
        dto.setClientAddress(asString(row.get("client_address")));
    }

    private Map<String, Object> getFirstRow(List<Map<String, Object>> results) {
        return (results == null || results.isEmpty()) ? null : results.get(0);
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
