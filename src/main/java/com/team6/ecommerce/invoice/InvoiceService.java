package com.team6.ecommerce.invoice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;

    /**
     * Create and save a new invoice.
     *
     * @param invoice Invoice object to save
     * @return Saved Invoice object
     */
    public Invoice createInvoice(Invoice invoice) {
        log.info("[InvoiceService][createInvoice] Creating invoice for order ID: {}", invoice.getOrderId());
        return invoiceRepo.save(invoice);
    }

    /**
     * Retrieve an invoice by its ID.
     *
     * @param invoiceId ID of the invoice to fetch
     * @return Optional containing the Invoice if found, empty otherwise
     */
    public Optional<Invoice> getInvoiceById(String invoiceId) {
        log.info("[InvoiceService][getInvoiceById] Fetching invoice with ID: {}", invoiceId);
        return invoiceRepo.findById(invoiceId);
    }

    /**
     * Retrieve all invoices.
     *
     * @return List of all invoices
     */
    public List<Invoice> getAllInvoices() {
        log.info("[InvoiceService][getAllInvoices] Fetching all invoices");
        return invoiceRepo.findAll();
    }


    /**
     * Delete an invoice by its ID.
     *
     * @param invoiceId ID of the invoice to delete
     * @return True if deleted, false otherwise
     */
    public boolean deleteInvoice(String invoiceId) {
        log.info("[InvoiceService][deleteInvoice] Deleting invoice with ID: {}", invoiceId);
        if (invoiceRepo.existsById(invoiceId)) {
            invoiceRepo.deleteById(invoiceId);
            return true;
        }
        return false;
    }


    /**
     * Retrieve all invoices for a specific user by their user ID.
     *
     * @param userId ID of the user
     * @return List of invoices belonging to the user
     */
    public List<Invoice> getInvoicesByUserId(String userId) {
        log.info("[InvoiceService][getInvoicesByUserId] Fetching invoices for user ID: {}", userId);
        return invoiceRepo.findByUserId(userId);
    }



    public byte[] generateInvoicePDF(Invoice invoice) {
        Document document = new Document();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE); // Add a blank line

            // Add invoice details
            Font detailsFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Invoice ID: " + invoice.getId(), detailsFont));
            document.add(new Paragraph("Order ID: " + invoice.getOrderId(), detailsFont));
            document.add(new Paragraph("User Email: " + invoice.getEmail(), detailsFont));
            document.add(new Paragraph("Total Amount: $" + invoice.getTotalAmount(), detailsFont));
            document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate(), detailsFont));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("[InvoiceService][generateInvoicePDF] Error generating PDF: {}", e.getMessage());
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}


