package com.goldencargo.service;

import com.goldencargo.model.entities.Invoice;
import com.goldencargo.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice();
        invoice.setInvoiceId(1L);
        invoice.setInvoiceNumber("INV-12345");
        invoice.setInvoiceType(Invoice.InvoiceType.CLIENT);
        invoice.setRelatedId(100L);
        invoice.setDateIssued(new Date());
        invoice.setTotalAmount(1500.0);
        invoice.setDueDate(new Date());
        invoice.setPaymentStatus(Invoice.PaymentStatus.UNPAID);
        invoice.setCreatedAt(new Date());
        invoice.setUpdatedAt(new Date());
    }

    @Test
    public void testGetAllInvoices() {
        List<Invoice> invoices = Collections.singletonList(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(invoice.getInvoiceNumber(), result.getFirst().getInvoiceNumber());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    public void testGetInvoiceById() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        Optional<Invoice> result = invoiceService.getInvoiceById(1L);

        assertTrue(result.isPresent());
        assertEquals(invoice.getInvoiceNumber(), result.get().getInvoiceNumber());
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateInvoice() {
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        Invoice result = invoiceService.createInvoice(invoice);

        assertNotNull(result);
        assertEquals(invoice.getInvoiceNumber(), result.getInvoiceNumber());
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    public void testUpdateInvoice() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        Optional<Invoice> result = invoiceService.updateInvoice(1L, invoice);

        assertTrue(result.isPresent());
        assertEquals(invoice.getInvoiceNumber(), result.get().getInvoiceNumber());
        verify(invoiceRepository, times(1)).findById(1L);
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    public void testDeleteInvoice() {
        when(invoiceRepository.existsById(1L)).thenReturn(true);
        doNothing().when(invoiceRepository).deleteById(1L);

        boolean result = invoiceService.deleteInvoice(1L);

        assertTrue(result);
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteInvoice_NotFound() {
        when(invoiceRepository.existsById(1L)).thenReturn(false);

        boolean result = invoiceService.deleteInvoice(1L);

        assertFalse(result);
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, never()).deleteById(1L);
    }
}