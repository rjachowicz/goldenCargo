package com.goldencargo.repository;

import com.goldencargo.model.dto.api.TransportOrderDetailsDTO;
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

    @Query("""
                SELECT new com.goldencargo.model.dto.api.TransportOrderDetailsDTO(
                    tror.transportOrderId,
                    tror.name,
                    CONCAT(d.user.firstName, ' ', d.user.lastName),
                    d.licenseNumber,
                    CONCAT(v.make, ' ', v.model, ' [', v.registrationNumber, ']'),
                    CONCAT(sl.name, ', ', sl.city),
                    CONCAT(el.name, ', ', el.city),
                    tror.status
                )
                FROM TransportOrder tror
                JOIN tror.assignedDriver d
                JOIN tror.assignedVehicle v
                JOIN tror.startLocation sl
                JOIN tror.endLocation el
            """)
    List<TransportOrderDetailsDTO> findAllTransportOrders();

}