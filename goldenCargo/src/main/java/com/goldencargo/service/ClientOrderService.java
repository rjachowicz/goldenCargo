package com.goldencargo.service;

import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.repository.ClientOrderRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;

    public ClientOrderService(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    public List<ClientOrder> getAllClientOrders() {
        return clientOrderRepository.findByIsDeletedFalse();
    }

    public Optional<ClientOrder> getClientOrderById(Long id) {
        return clientOrderRepository.findById(id);
    }

    @Transactional
    public Optional<ClientOrder> getClientOrderByIdWithDetails(Long id) {
        Optional<ClientOrder> clientOrder = clientOrderRepository.findById(id);
        clientOrder.ifPresent(order -> {
            Hibernate.initialize(order.getTransportOrders());
            Hibernate.initialize(order.getClientInvoices());
        });
        return clientOrder;
    }

    public ClientOrder createClientOrder(ClientOrder clientOrder) {
        return clientOrderRepository.save(clientOrder);
    }

    public Optional<ClientOrder> updateClientOrder(Long id, ClientOrder clientOrderDetails) {
        return clientOrderRepository.findById(id).map(clientOrder -> {
            clientOrder.setClient(clientOrderDetails.getClient());
            clientOrder.setOrderDate(clientOrderDetails.getOrderDate());
            clientOrder.setStatus(clientOrderDetails.getStatus());
            clientOrder.setTotalAmount(clientOrderDetails.getTotalAmount());
            clientOrder.setPaymentStatus(clientOrderDetails.getPaymentStatus());
            clientOrder.setUpdatedAt(new java.util.Date());
            return clientOrderRepository.save(clientOrder);
        });
    }

    public boolean deleteClientOrder(Long id) {
        if (clientOrderRepository.existsById(id)) {
            clientOrderRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public List<ClientOrder> getClientOrdersWithGoods() {
        return clientOrderRepository.getClientOrdersWithGoods();
    }

    public List<ClientOrder> getClientOrdersByIds(List<Long> ids) {
        return clientOrderRepository.findAllById(ids);
    }

    public List<ClientOrder> getAllNewClientOrders() {
        return clientOrderRepository.findByIsDeletedFalseAndStatusIsNew();
    }
}