package com.team6.ecommerce;

import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddCategoryTest {

    @Test
    public void testAddCategory_Success() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category newCategory = new Category();
        newCategory.setName("New Category");

        when(categoryRepository.save(newCategory)).thenReturn(newCategory);

        CategoryService categoryService = new CategoryService(categoryRepository, null);

        // Act
        Category result = categoryService.save(newCategory);

        // Assert
        assertNotNull(result, "Saved category should not be null");
        assertEquals("New Category", result.getName(), "Category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).save(newCategory);
    }
}