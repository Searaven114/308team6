package com.team6.ecommerce;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import com.team6.ecommerce.product.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddNewProductTest {

    @Test
    public void testAddNewProduct_Success() {
        // Arrange
        ProductRepository productRepository = mock(ProductRepository.class);

        // Fake ProductDTO to simulate the input
        ProductDTO fakeProduct = ProductDTO.builder()
                .title("Gaming Laptop")
                .categoryId("electronics")
                .brand("Alienware")
                .model("M15 R7")
                .serialNumber("SN12345678")
                .description("High-performance gaming laptop with RGB lighting.")
                .quantityInStock(25)
                .basePrice(1899.99)
                .warrantyStatus(true)
                .distributorId("distr001")
                .build();

        // Product object the repository would return after save
        Product expectedSavedProduct = Product.builder()
                .id("product-unique-id") // Mock ID generation by the database
                .title("Gaming Laptop")
                .categoryId("electronics")
                .brand("Alienware")
                .model("M15 R7")
                .serialNumber("SN12345678")
                .description("High-performance gaming laptop with RGB lighting.")
                .quantityInStock(25)
                .basePrice(1899.99)
                .warrantyStatus(true)
                .distributorId("distr001")
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(expectedSavedProduct);

        ProductService productService = new ProductService(productRepository);

        // Act
        Product result = productService.addProduct(fakeProduct);

        // Assert
        assertNotNull(result, "Saved product should not be null");
        assertEquals(expectedSavedProduct.getId(), result.getId(), "Product ID should match");
        assertEquals(expectedSavedProduct.getTitle(), result.getTitle(), "Product title should match");
        assertEquals(expectedSavedProduct.getBasePrice(), result.getBasePrice(), "Product price should match");
        assertEquals(expectedSavedProduct.getQuantityInStock(), result.getQuantityInStock(), "Product stock quantity should match");

        // Verify repository interaction
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
