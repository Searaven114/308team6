package com.team6.ecommerce;

import com.team6.ecommerce.cartitem.CartItem2;
import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceRepository;
import com.team6.ecommerce.invoice.InvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepo;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    void testCreateInvoice() {
        Invoice invoice = Invoice.builder()
                .id("1")
                .orderId("1001")
                .userId("user1")
                .totalAmount(200.0)
                .email("user1@example.com")
                .build();

        when(invoiceRepo.save(invoice)).thenReturn(invoice);

        Invoice result = invoiceService.createInvoice(invoice);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(invoiceRepo, times(1)).save(invoice);
    }

    @Test
    void testGetInvoiceById() {
        String invoiceId = "1";
        Invoice invoice = Invoice.builder().id(invoiceId).build();

        when(invoiceRepo.findById(invoiceId)).thenReturn(Optional.of(invoice));

        Optional<Invoice> result = invoiceService.getInvoiceById(invoiceId);

        assertTrue(result.isPresent());
        assertEquals(invoiceId, result.get().getId());
        verify(invoiceRepo, times(1)).findById(invoiceId);
    }

    @Test
    void testGetAllInvoices() {
        List<Invoice> invoices = List.of(
                Invoice.builder().id("1").build(),
                Invoice.builder().id("2").build()
        );

        when(invoiceRepo.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertEquals(2, result.size());
        verify(invoiceRepo, times(1)).findAll();
    }

    @Test
    void testDeleteInvoice_Success() {
        String invoiceId = "1";

        when(invoiceRepo.existsById(invoiceId)).thenReturn(true);

        boolean result = invoiceService.deleteInvoice(invoiceId);

        assertTrue(result);
        verify(invoiceRepo, times(1)).deleteById(invoiceId);
    }

    @Test
    void testDeleteInvoice_Failure() {
        String invoiceId = "1";

        when(invoiceRepo.existsById(invoiceId)).thenReturn(false);

        boolean result = invoiceService.deleteInvoice(invoiceId);

        assertFalse(result);
        verify(invoiceRepo, never()).deleteById(any());
    }

    @Test
    void testGetInvoicesByUserId() {
        String userId = "user1";
        List<Invoice> invoices = List.of(
                Invoice.builder().userId(userId).build()
        );

        when(invoiceRepo.findByUserId(userId)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getInvoicesByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(invoiceRepo, times(1)).findByUserId(userId);
    }

    @Test
    void testGenerateInvoicePDF() {
        Invoice invoice = Invoice.builder()
                .id("1")
                .orderId("1001")
                .userId("user1")
                .totalAmount(200.0)
                .email("user1@example.com")
                .purchasedItems(List.of(
                        CartItem2.builder().productName("Product A").quantity(2).price(50.0).build(),
                        CartItem2.builder().productName("Product B").quantity(1).price(100.0).build()
                ))
                .build();

        byte[] pdf = invoiceService.generateInvoicePDF(invoice);

        assertNotNull(pdf);
        assertTrue(pdf.length > 0); // Basic validation
    }

    @Test
    void testGetAllInvoices_Empty() {
        when(invoiceRepo.findAll()).thenReturn(List.of());

        List<Invoice> result = invoiceService.getAllInvoices();

        assertTrue(result.isEmpty());
        verify(invoiceRepo, times(1)).findAll();
    }
}