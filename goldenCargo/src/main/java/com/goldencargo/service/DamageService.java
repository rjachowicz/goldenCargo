package com.goldencargo.service;

import com.goldencargo.model.entities.Damage;
import com.goldencargo.repository.DamageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DamageService {

    private final DamageRepository damageRepository;

    public DamageService(DamageRepository damageRepository) {
        this.damageRepository = damageRepository;
    }

    public List<Damage> getAllDamages() {
        return damageRepository.findAll();
    }

    public Optional<Damage> getDamageById(Long id) {
        return damageRepository.findById(id);
    }

    public Damage createDamage(Damage damage) {
        return damageRepository.save(damage);
    }

    public Optional<Damage> updateDamage(Long id, Damage damageDetails) {
        return damageRepository.findById(id).map(damage -> {
            damage.setIncident(damageDetails.getIncident());
            damage.setDescription(damageDetails.getDescription());
            damage.setRepairCost(damageDetails.getRepairCost());
            damage.setInsuranceClaim(damageDetails.getInsuranceClaim());
            damage.setClaimNumber(damageDetails.getClaimNumber());
            damage.setUpdatedAt(new java.util.Date());
            return damageRepository.save(damage);
        });
    }

    public boolean deleteDamage(Long id) {
        if (damageRepository.existsById(id)) {
            damageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
