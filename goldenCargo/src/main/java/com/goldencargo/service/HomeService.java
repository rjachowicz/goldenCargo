package com.goldencargo.service;

import com.goldencargo.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final HomeRepository homeRepository;

    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public List<String> getUserRoleLabels() {
        return homeRepository.getUserRoleDistribution()
                .stream()
                .map(row -> (String) row.get("roleName"))
                .collect(Collectors.toList());
    }

    public List<Integer> getUserRoleData() {
        return homeRepository.getUserRoleDistribution()
                .stream()
                .map(row -> ((Long) row.get("userCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getOrderStatusLabels() {
        return homeRepository.getOrderStatusDistribution()
                .stream()
                .map(row -> (String) row.get("orderStatus"))
                .collect(Collectors.toList());
    }

    public List<Integer> getOrderStatusData() {
        return homeRepository.getOrderStatusDistribution()
                .stream()
                .map(row -> ((Long) row.get("orderCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getVehicleStatusLabels() {
        return homeRepository.getVehicleStatusDistribution()
                .stream()
                .map(row -> (String) row.get("vehicleStatus"))
                .collect(Collectors.toList());
    }

    public List<Integer> getVehicleStatusData() {
        return homeRepository.getVehicleStatusDistribution()
                .stream()
                .map(row -> ((Long) row.get("vehicleCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getMessageStatusLabels() {
        return homeRepository.getMessageReadStatus()
                .stream()
                .map(row -> ((Boolean) row.get("readStatus")) ? "Read" : "Unread")
                .collect(Collectors.toList());
    }

    public List<Integer> getMessageStatusData() {
        return homeRepository.getMessageReadStatus()
                .stream()
                .map(row -> ((Long) row.get("messageCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getIncidentTypeLabels() {
        return homeRepository.getIncidentTypeDistribution()
                .stream()
                .map(row -> (String) row.get("incidentType"))
                .collect(Collectors.toList());
    }

    public List<Integer> getIncidentTypeData() {
        return homeRepository.getIncidentTypeDistribution()
                .stream()
                .map(row -> ((Long) row.get("incidentCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getVehicleRepairLabels() {
        return homeRepository.getVehicleRepairCosts()
                .stream()
                .map(row -> (String) row.get("vehicle"))
                .collect(Collectors.toList());
    }

    public List<Double> getVehicleRepairData() {
        return homeRepository.getVehicleRepairCosts()
                .stream()
                .map(row -> (Double) row.get("totalCost"))
                .collect(Collectors.toList());
    }

    public List<String> getTransportStatusLabels() {
        return homeRepository.getTransportStatuses()
                .stream()
                .map(row -> (String) row.get("transportStatus"))
                .collect(Collectors.toList());
    }

    public List<Integer> getTransportStatusData() {
        return homeRepository.getTransportStatuses()
                .stream()
                .map(row -> ((Long) row.get("transportCount")).intValue())
                .collect(Collectors.toList());
    }

    public List<String> getClientInvoiceStatusLabels() {
        return homeRepository.getClientInvoiceStatuses()
                .stream()
                .map(row -> (String) row.get("invoiceStatus"))
                .collect(Collectors.toList());
    }

    public List<Integer> getClientInvoiceStatusData() {
        return homeRepository.getClientInvoiceStatuses()
                .stream()
                .map(row -> ((Long) row.get("invoiceCount")).intValue())
                .collect(Collectors.toList());
    }
}
