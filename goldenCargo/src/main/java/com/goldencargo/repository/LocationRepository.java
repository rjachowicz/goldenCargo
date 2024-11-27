package com.goldencargo.repository;

import com.goldencargo.model.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Location b SET b.isDeleted = true WHERE b.locationId = :id")
    void softDelete(@Param("id") Long id);
}