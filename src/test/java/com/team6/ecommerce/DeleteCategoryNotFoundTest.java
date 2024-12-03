package com.team6.ecommerce;

import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
public class DeleteCategoryNotFoundTest {

   @Test
    public void testDeleteCategory_NotFound() {
        // Arrange
        String categoryId = "999";

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(categoryId));
        assertEquals("Category not found", exception.getMessage(), "Expected 'Category not found' exception message");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }
}
*/
