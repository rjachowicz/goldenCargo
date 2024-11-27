package com.goldencargo.service;

import com.goldencargo.model.entities.Logistics;
import com.goldencargo.repository.LogisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogisticsService {

    private final LogisticsRepository logisticsRepository;

    public LogisticsService(LogisticsRepository logisticsRepository) {
        this.logisticsRepository = logisticsRepository;
    }

    public List<Logistics> getAllLogistics() {
        return logisticsRepository.findByIsDeletedFalse();
    }

    public Optional<Logistics> getLogisticById(Long id) {
        return logisticsRepository.findById(id);
    }

    public void createLogistics(Logistics logistic) {
        logisticsRepository.save(logistic);
    }

    public void updateLogistics(Long id, Logistics logisticDetails) {
        logisticsRepository.findById(id).map(logistic -> {
            logistic.setUser(logisticDetails.getUser());
            logistic.setDepartment(logisticDetails.getDepartment());
            logistic.setUpdatedAt(new java.util.Date());
            return logisticsRepository.save(logistic);
        });
    }

    public boolean deleteLogistics(Long id) {
        if (logisticsRepository.existsById(id)) {
            logisticsRepository.forceDelete(id);
            return true;
        }
        return false;
    }
}
