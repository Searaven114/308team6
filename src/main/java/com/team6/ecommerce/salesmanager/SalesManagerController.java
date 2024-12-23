package com.team6.ecommerce.salesmanager;


import com.team6.ecommerce.exception.ProductNotFoundException;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RequestMapping("/api/sm")
@RestController
public class SalesManagerController {

    private final SalesManagerService salesManagerService;
    private final ProductService productService;
    private final ProductRepository productRepo;
    //private final NotificationService notificationService;


    @Secured({"ROLE_SALESMANAGER", "ROLE_ADMIN"})
    @PostMapping("/product/{id}/update-price/{price}")
    public ResponseEntity<?> updateProductPrice(@PathVariable String id, @PathVariable double price) {

        try {
            if (price <= 0){
                return ResponseEntity.badRequest().body("Price cannot be equal or lower than 0");
            }

            Product updatedProduct = productService.updateProductPrice(id, price);

            //notificationService.notifyUsersAboutPriceChange(...);

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
            return ResponseEntity.badRequest().body("Discount rate must be between 0 and 100."); //invalidargumentexception de döndürebilir belki
        }

        Product discountedProduct = productService.applyDiscount(id, discountRate);

        //notificationService.notifyUsersAboutDiscount(...);

        return ResponseEntity.ok(discountedProduct);


    }







}
