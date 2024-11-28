package com.goldencargo.service;

import com.goldencargo.model.entities.TechnicalInspection;
import com.goldencargo.repository.TechnicalInspectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalInspectionService {

    private final TechnicalInspectionRepository technicalInspectionRepository;

    public TechnicalInspectionService(TechnicalInspectionRepository technicalInspectionRepository) {
        this.technicalInspectionRepository = technicalInspectionRepository;
    }

    public List<TechnicalInspection> getAllInspections() {
        return technicalInspectionRepository.findByIsDeletedFalse();
    }

    public Optional<TechnicalInspection> getInspectionById(Long id) {
        return technicalInspectionRepository.findById(id);
    }

    public TechnicalInspection createInspection(TechnicalInspection inspection) {
        return technicalInspectionRepository.save(inspection);
    }

    public Optional<TechnicalInspection> updateInspection(Long id, TechnicalInspection inspectionDetails) {
        return technicalInspectionRepository.findById(id).map(inspection -> {
            inspection.setVehicle(inspectionDetails.getVehicle());
            inspection.setInspectionDate(inspectionDetails.getInspectionDate());
            inspection.setResult(inspectionDetails.getResult());
            inspection.setComments(inspectionDetails.getComments());
            inspection.setNextInspectionDate(inspectionDetails.getNextInspectionDate());
            inspection.setInspectorName(inspectionDetails.getInspectorName());
            inspection.setUpdatedAt(new java.util.Date());
            return technicalInspectionRepository.save(inspection);
        });
    }

    public boolean deleteInspection(Long id) {
        if (technicalInspectionRepository.existsById(id)) {
            technicalInspectionRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
