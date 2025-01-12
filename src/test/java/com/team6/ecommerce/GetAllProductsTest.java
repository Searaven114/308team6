package com.team6.ecommerce;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllProductsTest {

    @Test
    public void testGetAllProducts_WhenProductsExist() {
        // Arrange
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository, null);

        Product product1 = new Product();
        product1.setId("1");
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId("2");
        product2.setTitle("Product 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertNotNull(products, "Products list should not be null");
        assertEquals(2, products.size(), "Products list size should be 2");
        assertEquals("Product 1", products.get(0).getTitle(), "First product title should match");
        assertEquals("Product 2", products.get(1).getTitle(), "Second product title should match");

        // Verify repository interaction
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllProducts_WhenNoProductsExist() {
        // Arrange
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository, null);

        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertNotNull(products, "Products list should not be null");
        assertEquals(0, products.size(), "Products list size should be 0");

        // Verify repository interaction
        verify(productRepository, times(1)).findAll();
    }
}