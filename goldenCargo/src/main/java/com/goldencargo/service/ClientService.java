package com.goldencargo.service;

import com.goldencargo.model.entities.Client;
import com.goldencargo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findByIsDeletedFalse();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setName(clientDetails.getName());
            client.setAddress(clientDetails.getAddress());
            client.setContactPerson(clientDetails.getContactPerson());
            client.setPhone(clientDetails.getPhone());
            client.setEmail(clientDetails.getEmail());
            client.setNip(clientDetails.getNip());
            client.setInvoiceType(clientDetails.getInvoiceType());
            client.setUpdatedAt(new java.util.Date());
            return clientRepository.save(client);
        });
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.softDelete(id);
            return true;
        }
        return false;
    }
}