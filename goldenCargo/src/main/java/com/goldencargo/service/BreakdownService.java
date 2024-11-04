package com.goldencargo.service;

import com.goldencargo.model.entities.Breakdown;
import com.goldencargo.repository.BreakdownRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;

    public BreakdownService(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    public List<Breakdown> getAllBreakdowns() {
        return breakdownRepository.findAll();
    }

    public Optional<Breakdown> getBreakdownById(Long id) {
        return breakdownRepository.findById(id);
    }

    public Breakdown createBreakdown(Breakdown breakdown) {
        return breakdownRepository.save(breakdown);
    }

    public Optional<Breakdown> updateBreakdown(Long id, Breakdown breakdownDetails) {
        return breakdownRepository.findById(id).map(breakdown -> {
            breakdown.setIncident(breakdownDetails.getIncident());
            breakdown.setDescription(breakdownDetails.getDescription());
            breakdown.setRepairCost(breakdownDetails.getRepairCost());
            breakdown.setRepairDate(breakdownDetails.getRepairDate());
            breakdown.setUpdatedAt(new java.util.Date());
            return breakdownRepository.save(breakdown);
        });
    }

    public boolean deleteBreakdown(Long id) {
        if (breakdownRepository.existsById(id)) {
            breakdownRepository.deleteById(id);
            return true;
        }
        return false;
    }
}