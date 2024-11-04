package com.goldencargo.controller;

import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.service.ClientOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client-orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientOrder>> getAllClientOrders() {
        List<ClientOrder> clientOrders = clientOrderService.getAllClientOrders();
        return new ResponseEntity<>(clientOrders, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ClientOrder> getClientOrderById(@PathVariable Long id) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderById(id);
        return clientOrder.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<ClientOrder> createClientOrder(@RequestBody ClientOrder clientOrder) {
        ClientOrder createdClientOrder = clientOrderService.createClientOrder(clientOrder);
        return new ResponseEntity<>(createdClientOrder, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientOrder> updateClientOrder(@PathVariable Long id, @RequestBody ClientOrder clientOrderDetails) {
        Optional<ClientOrder> updatedClientOrder = clientOrderService.updateClientOrder(id, clientOrderDetails);
        return updatedClientOrder.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientOrder(@PathVariable Long id) {
        boolean isDeleted = clientOrderService.deleteClientOrder(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}