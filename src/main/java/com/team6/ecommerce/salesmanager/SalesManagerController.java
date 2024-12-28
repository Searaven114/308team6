package com.team6.ecommerce.salesmanager;


import com.team6.ecommerce.exception.ProductNotFoundException;
import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Log4j2
@AllArgsConstructor
@RequestMapping("/api/sm")
@RestController
public class SalesManagerController {

    private final SalesManagerService salesManagerService;
    private final ProductService productService;
    private final ProductRepository productRepo;
    private final NotificationService notificationService;
    private final InvoiceService invoiceService;

    @Secured({"ROLE_SALESMANAGER", "ROLE_ADMIN"})
    @PostMapping("/product/{id}/update-price/{price}")
    public ResponseEntity<?> updateProductPrice(@PathVariable String id, @PathVariable double price) {

        try {
            if (price <= 0){
                return ResponseEntity.badRequest().body("Price cannot be equal or lower than 0");
            }
            Product updatedProduct = productService.updateProductPrice(id, price);
            return ResponseEntity.ok(updatedProduct);

        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }



    @Secured({"ROLE_SALESMANAGER", "ROLE_ADMIN"})
    @PostMapping("/product/{id}/apply-discount/{discountRate}")
    public ResponseEntity<?> applyDiscount(@PathVariable String id, @PathVariable double discountRate) {

        if (discountRate < 0 || discountRate > 100) {
            return ResponseEntity.badRequest().body("Discount rate must be between 0 and 100.");
        }

        Product discountedProduct = productService.applyDiscount(id, discountRate);

        notificationService.notifyUsersAboutDiscount(id, discountRate);

        return ResponseEntity.ok(discountedProduct);
    }


    @Secured({"ROLE_SALESMANAGER", "ROLE_ADMIN"})
    @GetMapping("/invoices")
    public ResponseEntity<?> viewInvoices(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    @Secured({"ROLE_SALESMANAGER", "ROLE_ADMIN"})
    @GetMapping("/invoices/{id}/download")
    public ResponseEntity<?> downloadInvoicePDF(@PathVariable String id) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));

            byte[] pdfContent = invoiceService.generateInvoicePDF(invoice);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "Invoice_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);

        } catch (Exception e) {
            log.error("[SalesManagerController][downloadInvoicePDF] Error generating invoice PDF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
        }
    }




}
