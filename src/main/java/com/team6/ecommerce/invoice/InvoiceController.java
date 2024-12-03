package com.team6.ecommerce.invoice;

import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // @PostMapping("/invoices")
    // public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
    //     Invoice newInvoice = invoiceService.createInvoice(invoice);
    //     return ResponseEntity.ok(newInvoice);
    // }

    // @GetMapping("/invoices/{id}")
    // public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
    //     Invoice invoice = invoiceService.getInvoiceById(id);
    //     return ResponseEntity.ok(invoice);
    // }

    // @GetMapping("/invoices")
    // public ResponseEntity<List<Invoice>> getAllInvoices() {
    //     List<Invoice> invoices = invoiceService.getAllInvoices();
    //     return ResponseEntity.ok(invoices);
    // }

    // @PutMapping("/invoices/{id}")
    // public ResponseEntity<Invoice> updateInvoice(@PathVariable String id, @RequestBody Invoice invoice) {
    //     Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice);
    //     return ResponseEntity.ok(updatedInvoice);
    // }

    // @DeleteMapping("/invoices/{id}")
    // public ResponseEntity<String> deleteInvoice(@PathVariable String id) {
    //     invoiceService.deleteInvoice(id);
    //     return ResponseEntity.ok("Invoice deleted successfully");
    // }
}
