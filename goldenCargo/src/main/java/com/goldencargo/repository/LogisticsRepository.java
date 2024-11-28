package com.goldencargo.repository;

import com.goldencargo.model.entities.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {

    @Query("select l from Logistics l join User u on l.user.userId = u.userId where l.isDeleted = false")
    List<Logistics> getAllLogistics();

    @Modifying
    @Transactional
    @Query("UPDATE Logistics b SET b.isDeleted = true WHERE b.logisticsId = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE from Logistics l where l.logisticsId = :id")
    void forceDelete(@Param("id") Long id);
}