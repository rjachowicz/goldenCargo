package com.goldencargo.repository;

import com.goldencargo.model.entities.Damage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DamageRepository extends JpaRepository<Damage, Long> {

    List<Damage> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Damage b SET b.isDeleted = true WHERE b.damageId = :id")
    void softDelete(@Param("id") Long id);
}