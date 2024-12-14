package com.goldencargo.repository;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.VehicleReportDTO;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.model.entities.VehicleRepairs;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CreateReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public CreateReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vehicle> findAllVehicles() {
        String sql = """
                     SELECT vehicle_id, make, model, registration_number, year, capacity, status, mileage
                     FROM vehicles\s
                     WHERE is_deleted = FALSE
                """;
        return jdbcTemplate.query(sql, this::mapVehicle);
    }

    public Optional<VehicleReportDTO> findVehicleReport(Long vehicleId) {
        String vehicleSql = """
                    SELECT * FROM vehicles WHERE vehicle_id = ? AND is_deleted = FALSE
                """;
        Vehicle vehicle = jdbcTemplate.queryForObject(vehicleSql, this::mapVehicle, vehicleId);

        if (vehicle == null) {
            return Optional.empty();
        }

        VehicleReportDTO report = new VehicleReportDTO();
        report.setVehicle(vehicle);
        report.setRepairs(findVehicleRepairs(vehicleId));
        report.setIncidents(findVehicleIncidents(vehicleId));
        report.setTransportOrders(findVehicleTransportOrders(vehicleId));

        return Optional.of(report);
    }

    private Vehicle mapVehicle(ResultSet rs, int rowNum) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(rs.getLong("vehicle_id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setRegistrationNumber(rs.getString("registration_number"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setCapacity(rs.getDouble("capacity"));
        vehicle.setMileage(rs.getInt("mileage"));
        vehicle.setStatus(Vehicle.VehicleStatus.valueOf(rs.getString("status")));
        return vehicle;
    }

    private List<VehicleRepairs> findVehicleRepairs(Long vehicleId) {
        String sql = """
                    SELECT * FROM vehicle_repairs WHERE vehicle_id = ? AND is_deleted = FALSE
                """;
        return jdbcTemplate.query(sql, this::mapVehicleRepair, vehicleId);
    }

    private VehicleRepairs mapVehicleRepair(ResultSet rs, int rowNum) throws SQLException {
        VehicleRepairs repair = new VehicleRepairs();
        repair.setServiceId(rs.getLong("service_id"));
        repair.setServiceDate(rs.getTimestamp("service_date"));
        repair.setServiceType(rs.getString("service_type"));
        repair.setDescription(rs.getString("description"));
        repair.setCost(rs.getDouble("cost"));
        repair.setServiceCenter(rs.getString("service_center"));
        repair.setNextServiceDue(rs.getTimestamp("next_service_due"));
        return repair;
    }

    private List<Incident> findVehicleIncidents(Long vehicleId) {
        String sql = """
                    SELECT * FROM incidents WHERE vehicle_id = ? AND is_deleted = FALSE
                """;
        return jdbcTemplate.query(sql, this::mapIncident, vehicleId);
    }

    private Incident mapIncident(ResultSet rs, int rowNum) throws SQLException {
        Incident incident = new Incident();
        incident.setIncidentId(rs.getLong("incident_id"));
        incident.setIncidentType(rs.getString("incident_type"));
        incident.setDate(rs.getTimestamp("date"));
        incident.setDescription(rs.getString("description"));
        incident.setStatus(Status.valueOf(rs.getString("status")));
        return incident;
    }

    private List<TransportOrder> findVehicleTransportOrders(Long vehicleId) {
        String sql = """
                    SELECT * FROM transport_orders WHERE assigned_vehicle_id = ? AND is_deleted = FALSE
                """;
        return jdbcTemplate.query(sql, this::mapTransportOrder, vehicleId);
    }

    private TransportOrder mapTransportOrder(ResultSet rs, int rowNum) throws SQLException {
        TransportOrder transportOrder = new TransportOrder();
        transportOrder.setTransportOrderId(rs.getLong("transport_order_id"));
        transportOrder.setName(rs.getString("transport_order_name"));
        transportOrder.setScheduledDeparture(rs.getTimestamp("scheduled_departure"));
        transportOrder.setScheduledArrival(rs.getTimestamp("scheduled_arrival"));
        transportOrder.setStatus(Status.valueOf(rs.getString("status")));
        return transportOrder;
    }
}
