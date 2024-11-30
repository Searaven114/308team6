package com.team6.ecommerce;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateProductPriceTest {

    @Test
    public void testUpdateProductPrice_Success() {
        // Arrange
        String productId = "product-1";
        double newPrice = 200.0;

        ProductRepository productRepository = mock(ProductRepository.class);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setTitle("Existing Product");
        existingProduct.setBasePrice(150.0);
        existingProduct.setQuantityInStock(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setTitle("Existing Product");
        updatedProduct.setBasePrice(newPrice);
        updatedProduct.setQuantityInStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        ProductService productService = new ProductService(productRepository);

        // Act
        Product result = productService.updateProductPrice(productId, newPrice);

        // Assert
        assertNotNull(result, "Updated product should not be null");
        assertEquals(productId, result.getId(), "Product ID should match");
        assertEquals(newPrice, result.getBasePrice(), "Product price should be updated");
        assertEquals(existingProduct.getQuantityInStock(), result.getQuantityInStock(), "Stock quantity should remain unchanged");

        // Verify interactions
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testUpdateProductPrice_WhenProductNotFound() {
        // Arrange
        String productId = "non-existent-product-id";
        double newPrice = 200.0;

        ProductRepository productRepository = mock(ProductRepository.class);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductService productService = new ProductService(productRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.updateProductPrice(productId, newPrice));
        assertEquals("Product not found", exception.getMessage(), "Expected 'Product not found' exception message");

        // Verify interactions
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }
}