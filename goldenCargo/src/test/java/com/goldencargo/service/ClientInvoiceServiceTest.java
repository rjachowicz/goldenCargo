package com.goldencargo.service;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.repository.ClientInvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientInvoiceServiceTest {

    @Mock
    private ClientInvoiceRepository clientInvoiceRepository;

    @InjectMocks
    private ClientInvoiceService clientInvoiceService;

    private ClientInvoice clientInvoice;

    @BeforeEach
    public void setUp() {
        clientInvoice = new ClientInvoice();
        clientInvoice.setInvoiceId(1L);
        clientInvoice.setInvoiceNumber("INV-001");
        clientInvoice.setDateIssued(new Date());
        clientInvoice.setTotalAmount(1000.0);
        clientInvoice.setDueDate(new Date());
        clientInvoice.setPaymentStatus(ClientInvoice.PaymentStatus.UNPAID);
        clientInvoice.setCreatedAt(new Date());
        clientInvoice.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllClientInvoices() {
        List<ClientInvoice> clientInvoices = Collections.singletonList(clientInvoice);
        when(clientInvoiceRepository.findAll()).thenReturn(clientInvoices);

        List<ClientInvoice> result = clientInvoiceService.getAllClientInvoices();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientInvoice.getInvoiceNumber(), result.getFirst().getInvoiceNumber());
        verify(clientInvoiceRepository, times(1)).findAll();
    }

    @Test
    public void testGetClientInvoiceById() {
        when(clientInvoiceRepository.findById(1L)).thenReturn(Optional.of(clientInvoice));

        Optional<ClientInvoice> result = clientInvoiceService.getClientInvoiceById(1L);

        assertTrue(result.isPresent());
        assertEquals(clientInvoice.getInvoiceNumber(), result.get().getInvoiceNumber());
        verify(clientInvoiceRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateClientInvoice() {
        when(clientInvoiceRepository.save(clientInvoice)).thenReturn(clientInvoice);

        ClientInvoice result = clientInvoiceService.createClientInvoice(clientInvoice);

        assertNotNull(result);
        assertEquals(clientInvoice.getInvoiceNumber(), result.getInvoiceNumber());
        verify(clientInvoiceRepository, times(1)).save(clientInvoice);
    }

    @Test
    public void testUpdateClientInvoice() {
        when(clientInvoiceRepository.findById(1L)).thenReturn(Optional.of(clientInvoice));
        when(clientInvoiceRepository.save(clientInvoice)).thenReturn(clientInvoice);

        Optional<ClientInvoice> result = clientInvoiceService.updateClientInvoice(1L, clientInvoice);

        assertTrue(result.isPresent());
        assertEquals(clientInvoice.getInvoiceNumber(), result.get().getInvoiceNumber());
        verify(clientInvoiceRepository, times(1)).findById(1L);
        verify(clientInvoiceRepository, times(1)).save(clientInvoice);
    }

    @Test
    public void testDeleteClientInvoice() {
        when(clientInvoiceRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientInvoiceRepository).deleteById(1L);

        boolean result = clientInvoiceService.deleteClientInvoice(1L);

        assertTrue(result);
        verify(clientInvoiceRepository, times(1)).existsById(1L);
        verify(clientInvoiceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteClientInvoice_NotFound() {
        when(clientInvoiceRepository.existsById(1L)).thenReturn(false);

        boolean result = clientInvoiceService.deleteClientInvoice(1L);

        assertFalse(result);
        verify(clientInvoiceRepository, times(1)).existsById(1L);
        verify(clientInvoiceRepository, never()).deleteById(1L);
    }
}