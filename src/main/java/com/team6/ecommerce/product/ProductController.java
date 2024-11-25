package com.team6.ecommerce.product;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    //@Secured({"ROLE_ADMIN"})
    @GetMapping("/get-all-products")
    public ResponseEntity<?> getAllProducts(){

        List<Product> products = productService.getAllProducts();
        if ( products.isEmpty() ){
            return ResponseEntity.ok("NO PRODUCTS RETURNED");
        } else {
            return ResponseEntity.ok().body(products);
        }
    }


    // Fetch paginated products without sorting
    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getPaginatedProducts(page, size);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/sort-by-price")
    public ResponseEntity<Page<Product>> getProductsSortedByPrice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<Product> products = productService.getProductsSortedByPrice(page, size, sortDirection);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/sort-by-popularity")
    public ResponseEntity<Page<Product>> getProductsSortedByPopularity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getProductsSortedByPopularity(page, size);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/sort-by-price-and-category")
    public ResponseEntity<Page<Product>> getProductsByCategoryAndSortedByPrice(
            @RequestParam String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<Product> products = productService.getProductsByCategoryAndSortedByPrice(categoryId, page, size, sortDirection);

        return ResponseEntity.ok(products);
    }

    // Paginated search endpoint
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.searchProducts(query, page, size);
        return ResponseEntity.ok(products);
    }



//    Get paginated products:            /api/product/paginated?page=0&size=10
//    Get products sorted by price:      /api/product/sort-by-price?page=0&size=10
//    Get products sorted by popularity: /api/product/sort-by-popularity?page=0&size=10


}
