package com.team6.ecommerce;

import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateCategoryTest {

    @Test
    public void testUpdateCategory_Success() {
        // Arrange
        String categoryId = "1";

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Old Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        Category result = categoryService.save(updatedCategory);

        // Assert
        assertNotNull(result, "Updated category should not be null");
        assertEquals("Updated Category", result.getName(), "Category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).save(updatedCategory);
    }
}
