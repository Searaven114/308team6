package com.team6.ecommerce;

import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryService;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilterProductsByCategoryTest {

//    @Test
//    public void testFilterProductsByCategory_Success() {
//        // Arrange
//        String categoryId = "electronics";
//
//        ProductRepository productRepository = mock(ProductRepository.class);
//        CategoryService categoryService = mock(CategoryService.class);
//
//        // Simulate the category for the given categoryId
//        Category electronicsCategory = new Category();
//        electronicsCategory.setId(categoryId);
//        electronicsCategory.setName("Electronics");
//
//        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(electronicsCategory));
//
//        // Mock products
//        Product product1 = new Product();
//        product1.setId("product-1");
//        product1.setTitle("Smartphone");
//        product1.setCategoryId(categoryId);
//
//        Product product2 = new Product();
//        product2.setId("product-2");
//        product2.setTitle("Laptop");
//        product2.setCategoryId(categoryId);
//
//        Product product3 = new Product();
//        product3.setId("product-3");
//        product3.setTitle("Shoes");
//        product3.setCategoryId("fashion");
//
//        List<Product> allProducts = Stream.of(product1, product2, product3).collect(Collectors.toList());
//        when(productRepository.findAll()).thenReturn(allProducts);
//
//        ProductService productService = new ProductService(productRepository);
//
//        // Act
//        List<Product> filteredProducts = productService.getProductsByCategory(categoryId);
//
//        // Assert
//        assertNotNull(filteredProducts, "Filtered products list should not be null");
//        assertEquals(2, filteredProducts.size(), "There should be 2 products in the 'Electronics' category");
//        assertTrue(
//                filteredProducts.stream().allMatch(p -> p.getCategoryId().equals(categoryId)),
//                "All products should belong to the 'Electronics' category"
//        );
//
//        // Verify repository interaction
//        verify(productRepository, times(1)).findAll();
//        verifyNoInteractions(categoryService); // Assuming `getProductsByCategory` only validates categoryId
//    }
//
//    @Test
//    public void testFilterProductsByCategory_WhenNoProductsExist() {
//        // Arrange
//        String categoryId = "nonExistentCategory";
//
//        ProductRepository productRepository = mock(ProductRepository.class);
//
//        when(productRepository.findAll()).thenReturn(List.of());
//
//        ProductService productService = new ProductService(productRepository);
//
//        // Act
//        List<Product> filteredProducts = productService.getProductsByCategory(categoryId);
//
//        // Assert
//        assertNotNull(filteredProducts, "Filtered products list should not be null");
//        assertTrue(filteredProducts.isEmpty(), "Filtered products list should be empty for a non-existent category");
//
//        // Verify repository interaction
//        verify(productRepository, times(1)).findAll();
//    }
}
