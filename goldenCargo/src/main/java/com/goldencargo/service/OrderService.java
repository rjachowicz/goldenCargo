package com.goldencargo.service;

import com.goldencargo.model.entities.Order;
import com.goldencargo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findByIsDeletedFalse();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Long id, Order orderDetails) {
        orderRepository.findById(id).map(order -> {
            order.setClientOrder(orderDetails.getClientOrder());
            order.setTransportOrder(orderDetails.getTransportOrder());
            order.setOrderType(orderDetails.getOrderType());
            order.setStatus(orderDetails.getStatus());
            order.setUpdatedAt(new java.util.Date());
            return orderRepository.save(order);
        });
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
