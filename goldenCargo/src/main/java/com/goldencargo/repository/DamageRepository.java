package com.goldencargo.repository;

import com.goldencargo.model.entities.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRepository extends JpaRepository<Damage, Long> {
}