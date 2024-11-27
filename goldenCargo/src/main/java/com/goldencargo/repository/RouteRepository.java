package com.goldencargo.repository;

import com.goldencargo.model.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Route b SET b.isDeleted = true WHERE b.routeId = :id")
    void softDelete(@Param("id") Long id);
}