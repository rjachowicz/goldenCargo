package com.goldencargo.repository;

import com.goldencargo.model.entities.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

    List<Breakdown> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Breakdown b SET b.isDeleted = true WHERE b.breakdownId = :id")
    void softDelete(@Param("id") Long id);
}
