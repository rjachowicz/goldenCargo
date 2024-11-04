package com.goldencargo.service;

import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.repository.ClientOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientOrderService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    public List<ClientOrder> getAllClientOrders() {
        return clientOrderRepository.findAll();
    }

    public Optional<ClientOrder> getClientOrderById(Long id) {
        return clientOrderRepository.findById(id);
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
            clientOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}