package com.goldencargo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class HomeRepository {

    private final JdbcTemplate jdbcTemplate;

    public HomeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getUserRoleDistribution() {
        String sql = """
                    SELECT r.name AS roleName, COUNT(u.user_id) AS userCount
                    FROM roles r
                    LEFT JOIN user_roles ur ON r.role_id = ur.role_id
                    LEFT JOIN users u ON ur.user_id = u.user_id
                    WHERE u.is_deleted = FALSE
                    GROUP BY r.name
                    ORDER BY userCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getOrderStatusDistribution() {
        String sql = """
                    SELECT co.status AS orderStatus, COUNT(co.client_order_id) AS orderCount
                    FROM client_orders co
                    WHERE co.is_deleted = FALSE
                    GROUP BY co.status
                    ORDER BY orderCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getVehicleStatusDistribution() {
        String sql = """
                    SELECT v.status AS vehicleStatus, COUNT(v.vehicle_id) AS vehicleCount
                    FROM vehicles v
                    WHERE v.is_deleted = FALSE
                    GROUP BY v.status
                    ORDER BY vehicleCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getMessageReadStatus() {
        String sql = """
                    SELECT m.read_status AS readStatus, COUNT(m.message_id) AS messageCount
                    FROM messages m
                    WHERE m.is_deleted = FALSE
                    GROUP BY m.read_status
                    ORDER BY messageCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getIncidentTypeDistribution() {
        String sql = """
                    SELECT i.incident_type AS incidentType, COUNT(i.incident_id) AS incidentCount
                    FROM incidents i
                    WHERE i.is_deleted = FALSE
                    GROUP BY i.incident_type
                    ORDER BY incidentCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getVehicleRepairCosts() {
        String sql = """
                    SELECT v.registration_number AS vehicle, SUM(r.cost) AS totalCost
                    FROM vehicles v
                    JOIN vehicle_repairs r ON v.vehicle_id = r.vehicle_id
                    WHERE v.is_deleted = FALSE AND r.is_deleted = FALSE
                    GROUP BY v.registration_number
                    ORDER BY totalCost DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTransportStatuses() {
        String sql = """
                    SELECT t.status AS transportStatus, COUNT(t.transport_id) AS transportCount
                    FROM transports t
                    WHERE t.is_deleted = FALSE
                    GROUP BY t.status
                    ORDER BY transportCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getClientInvoiceStatuses() {
        String sql = """
                    SELECT ci.payment_status AS invoiceStatus, COUNT(ci.invoice_id) AS invoiceCount
                    FROM client_invoices ci
                    WHERE ci.is_deleted = FALSE
                    GROUP BY ci.payment_status
                    ORDER BY invoiceCount DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }
}
