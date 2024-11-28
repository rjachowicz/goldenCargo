package com.goldencargo.repository;

import com.goldencargo.model.entities.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {

    List<ClientOrder> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE ClientOrder b SET b.isDeleted = true WHERE b.clientOrderId = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT status, COUNT(clientOrderId) AS count FROM ClientOrder GROUP BY status ORDER BY count DESC")
    List<Object[]> getOrderDistribution();
}