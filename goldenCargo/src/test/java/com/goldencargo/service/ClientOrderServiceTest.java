package com.goldencargo.service;

import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.repository.ClientOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientOrderServiceTest {

    @Mock
    private ClientOrderRepository clientOrderRepository;

    @InjectMocks
    private ClientOrderService clientOrderService;

    private ClientOrder clientOrder;

    @BeforeEach
    public void setUp() {
        clientOrder = new ClientOrder();
        clientOrder.setClientOrderId(1L);
        clientOrder.setOrderDate(new Date());
        clientOrder.setStatus(ClientOrder.Status.PENDING);
        clientOrder.setTotalAmount(1500.0);
        clientOrder.setPaymentStatus(ClientOrder.PaymentStatus.UNPAID);
        clientOrder.setCreatedAt(new Date());
        clientOrder.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllClientOrders() {
        List<ClientOrder> clientOrders = Arrays.asList(clientOrder);
        when(clientOrderRepository.findAll()).thenReturn(clientOrders);

        List<ClientOrder> result = clientOrderService.getAllClientOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clientOrder.getStatus(), result.get(0).getStatus());
        verify(clientOrderRepository, times(1)).findAll();
    }

    @Test
    public void testGetClientOrderById() {
        when(clientOrderRepository.findById(1L)).thenReturn(Optional.of(clientOrder));

        Optional<ClientOrder> result = clientOrderService.getClientOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(clientOrder.getStatus(), result.get().getStatus());
        verify(clientOrderRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateClientOrder() {
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        ClientOrder result = clientOrderService.createClientOrder(clientOrder);

        assertNotNull(result);
        assertEquals(clientOrder.getStatus(), result.getStatus());
        verify(clientOrderRepository, times(1)).save(clientOrder);
    }

    @Test
    public void testUpdateClientOrder() {
        when(clientOrderRepository.findById(1L)).thenReturn(Optional.of(clientOrder));
        when(clientOrderRepository.save(clientOrder)).thenReturn(clientOrder);

        Optional<ClientOrder> result = clientOrderService.updateClientOrder(1L, clientOrder);

        assertTrue(result.isPresent());
        assertEquals(clientOrder.getStatus(), result.get().getStatus());
        verify(clientOrderRepository, times(1)).findById(1L);
        verify(clientOrderRepository, times(1)).save(clientOrder);
    }

    @Test
    public void testDeleteClientOrder() {
        when(clientOrderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientOrderRepository).deleteById(1L);

        boolean result = clientOrderService.deleteClientOrder(1L);

        assertTrue(result);
        verify(clientOrderRepository, times(1)).existsById(1L);
        verify(clientOrderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteClientOrder_NotFound() {
        when(clientOrderRepository.existsById(1L)).thenReturn(false);

        boolean result = clientOrderService.deleteClientOrder(1L);

        assertFalse(result);
        verify(clientOrderRepository, times(1)).existsById(1L);
        verify(clientOrderRepository, never()).deleteById(1L);
    }
}
