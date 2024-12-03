package com.team6.ecommerce;

import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCategoryByIdTest {

    @Test
    public void testGetCategoryById_WhenCategoryExists() {
        // Arrange
        String categoryId = "123";
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        Category mockCategory = new Category();
        mockCategory.setId(categoryId);
        mockCategory.setName("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        // Act
        Optional<Category> result = categoryRepository.findById(categoryId);

        // Assert
        assertTrue(result.isPresent(), "Category should be found");
        assertEquals("Test Category", result.get().getName(), "Category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testGetCategoryById_WhenCategoryDoesNotExist() {
        // Arrange
        String categoryId = "123";
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryRepository.findById(categoryId);

        // Assert
        assertFalse(result.isPresent(), "Category should not be found");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
    }
}
