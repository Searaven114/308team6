package com.team6.ecommerce;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetProductByIdTest {

    @Test
    public void testGetProductById_WhenProductExists() throws Exception {
        // Arrange
        String productId = "1";

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setTitle("Test Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Product product = productService.getProductById(productId);

        // Assert
        assertNotNull(product, "Product should not be null");
        assertEquals(productId, product.getId(), "Product ID should match");
        assertEquals("Test Product", product.getTitle(), "Product title should match");

        // Verify repository interaction
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testGetProductById_WhenProductDoesNotExist() throws Exception {
        // Arrange
        String productId = "999";

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> productService.getProductById(productId));
        assertEquals("Product not found", exception.getMessage(), "Expected 'Product not found' exception message");

        // Verify repository interaction
        verify(productRepository, times(1)).findById(productId);
    }
}
