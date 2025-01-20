package com.goldencargo.repository;

import com.goldencargo.model.entities.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransportOrderRepository extends JpaRepository<TransportOrder, Long> {

    List<TransportOrder> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE TransportOrder b SET b.isDeleted = true WHERE b.transportOrderId = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT u FROM TransportOrder u WHERE u.transportOrderId NOT IN (SELECT t.transportOrder.transportOrderId FROM Transport t) AND u.isDeleted = false")
    List<TransportOrder> findTransportOrdersNotAssignedToTransport();

    @Query("SELECT u FROM TransportOrder u WHERE u.transportOrderId NOT IN (SELECT t.transportOrder.transportOrderId FROM Transport t) AND u.isDeleted = false AND u.status = 'NEW'")
    List<TransportOrder> findTransportOrdersWithStatusNew();
}