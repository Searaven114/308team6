package com.team6.ecommerce;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilterOutOfStockProductsTest {

//    @Test
//    public void testFilterOutOfStockProducts_Success() {
//        // Arrange
//        ProductRepository productRepository = mock(ProductRepository.class);
//
//        Product product1 = new Product();
//        product1.setId("product-1");
//        product1.setTitle("Smartphone");
//        product1.setQuantityInStock(10);
//
//        Product product2 = new Product();
//        product2.setId("product-2");
//        product2.setTitle("Laptop");
//        product2.setQuantityInStock(0); // Out of stock
//
//        Product product3 = new Product();
//        product3.setId("product-3");
//        product3.setTitle("Tablet");
//        product3.setQuantityInStock(5);
//
//        List<Product> allProducts = Stream.of(product1, product2, product3).collect(Collectors.toList());
//
//        when(productRepository.findAll()).thenReturn(allProducts);
//
//        ProductService productService = new ProductService(productRepository);
//
//        // Act
//        List<Product> availableProducts = productService.getAvailableProducts();
//
//        // Assert
//        assertNotNull(availableProducts, "Available products list should not be null");
//        assertEquals(2, availableProducts.size(), "There should be 2 products in stock");
//        assertTrue(availableProducts.stream().allMatch(p -> p.getQuantityInStock() > 0), "All products should have stock greater than 0");
//
//        // Verify repository interaction
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testFilterOutOfStockProducts_WhenNoProductsInStock() {
//        // Arrange
//        ProductRepository productRepository = mock(ProductRepository.class);
//
//        Product product1 = new Product();
//        product1.setId("product-1");
//        product1.setTitle("Smartphone");
//        product1.setQuantityInStock(0); // Out of stock
//
//        Product product2 = new Product();
//        product2.setId("product-2");
//        product2.setTitle("Laptop");
//        product2.setQuantityInStock(0); // Out of stock
//
//        List<Product> allProducts = Stream.of(product1, product2).collect(Collectors.toList());
//
//        when(productRepository.findAll()).thenReturn(allProducts);
//
//        ProductService productService = new ProductService(productRepository);
//
//        // Act
//        List<Product> availableProducts = productService.getAvailableProducts();
//
//        // Assert
//        assertNotNull(availableProducts, "Available products list should not be null");
//        assertTrue(availableProducts.isEmpty(), "Available products list should be empty if no products are in stock");
//
//        // Verify repository interaction
//        verify(productRepository, times(1)).findAll();
//    }
}