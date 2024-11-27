package com.goldencargo.repository;

import com.goldencargo.model.entities.Transport;
import com.goldencargo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Transport b SET b.isDeleted = true WHERE b.transportId = :id")
    void softDelete(@Param("id") Long id);
}