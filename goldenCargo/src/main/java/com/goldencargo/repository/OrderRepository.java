package com.goldencargo.repository;

import com.goldencargo.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Order b SET b.isDeleted = true WHERE b.orderId = :id")
    void softDelete(@Param("id") Long id);
}