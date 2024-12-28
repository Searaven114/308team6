package com.team6.ecommerce.invoice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Secured({"ROLE_SALESMANAGER"})
@RestController("/api")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

     @PostMapping("/invoices")
     public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
         Invoice newInvoice = invoiceService.createInvoice(invoice);
         return ResponseEntity.ok(newInvoice);
     }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(id);

        return invoiceOptional
                .map(ResponseEntity::ok) // If present, return the invoice with 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 Not Found
    }

     @GetMapping("/invoices")
     public ResponseEntity<List<Invoice>> getAllInvoices() {
         List<Invoice> invoices = invoiceService.getAllInvoices();
         return ResponseEntity.ok(invoices);
     }


     @DeleteMapping("/invoices/{id}")
     public ResponseEntity<String> deleteInvoice(@PathVariable String id) {
         invoiceService.deleteInvoice(id);
         return ResponseEntity.ok("Invoice deleted successfully");
     }
}
