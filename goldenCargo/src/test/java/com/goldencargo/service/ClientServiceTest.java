package com.goldencargo.service;

import com.goldencargo.model.entities.Client;
import com.goldencargo.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setClientId(1L);
        client.setName("Test Client");
        client.setAddress("123 Main St");
        client.setContactPerson("John Doe");
        client.setPhone("123-456-7890");
        client.setEmail("test@example.com");
        client.setTaxId("123456789");
        client.setCreatedAt(new Date());
        client.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllClients() {
        List<Client> clients = Collections.singletonList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(client.getName(), result.getFirst().getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void testGetClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClientById(1L);

        assertTrue(result.isPresent());
        assertEquals(client.getName(), result.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateClient() {
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.createClient(client);

        assertNotNull(result);
        assertEquals(client.getName(), result.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testUpdateClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        Optional<Client> result = clientService.updateClient(1L, client);

        assertTrue(result.isPresent());
        assertEquals(client.getName(), result.get().getName());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testDeleteClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        boolean result = clientService.deleteClient(1L);

        assertTrue(result);
        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteClient_NotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        boolean result = clientService.deleteClient(1L);

        assertFalse(result);
        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, never()).deleteById(1L);
    }
}