package com.goldencargo.service;

import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.repository.TransportOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportOrderService {

    private final TransportOrderRepository transportOrderRepository;

    public TransportOrderService(TransportOrderRepository transportOrderRepository) {
        this.transportOrderRepository = transportOrderRepository;
    }

    public List<TransportOrder> getAllOrders() {
        return transportOrderRepository.findByIsDeletedFalse();
    }

    public Optional<TransportOrder> getOrderById(Long id) {
        return transportOrderRepository.findById(id);
    }

    public TransportOrder createOrder(TransportOrder order) {
        return transportOrderRepository.save(order);
    }

    public Optional<TransportOrder> updateOrder(Long id, TransportOrder orderDetails) {
        return transportOrderRepository.findById(id).map(order -> {
            order.setClientOrder(orderDetails.getClientOrder());
            order.setName(orderDetails.getName());
            order.setAssignedDriver(orderDetails.getAssignedDriver());
            order.setAssignedVehicle(orderDetails.getAssignedVehicle());
            order.setStartLocation(orderDetails.getStartLocation());
            order.setEndLocation(orderDetails.getEndLocation());
            order.setScheduledDeparture(orderDetails.getScheduledDeparture());
            order.setScheduledArrival(orderDetails.getScheduledArrival());
            order.setStatus(orderDetails.getStatus());
            order.setUpdatedAt(new java.util.Date());
            return transportOrderRepository.save(order);
        });
    }

    public boolean deleteOrder(Long id) {
        if (transportOrderRepository.existsById(id)) {
            transportOrderRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public List<TransportOrder> getTransportOrdersNotAssignedToTransport() {
        return transportOrderRepository.findTransportOrdersNotAssignedToTransport();
    }
}
