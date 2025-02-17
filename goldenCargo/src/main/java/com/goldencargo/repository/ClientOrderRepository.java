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

    @Query("SELECT co from ClientOrder co where co.isDeleted is false and co.status = 'NEW'")
    List<ClientOrder> findByIsDeletedFalseAndStatusIsNew();

    @Modifying
    @Transactional
    @Query("UPDATE ClientOrder b SET b.isDeleted = true WHERE b.clientOrderId = :id")
    void softDelete(@Param("id") Long id);

    @Query("SELECT DISTINCT co FROM ClientOrder co LEFT JOIN FETCH co.goods where co.status = 'NEW'")
    List<ClientOrder> getClientOrdersWithGoods();

}