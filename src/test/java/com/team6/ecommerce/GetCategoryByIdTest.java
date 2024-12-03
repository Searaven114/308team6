/*import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCategoryByIdTest {

    @Test
    public void testGetCategoryById_Success() {
        // Arrange
        String categoryId = "1";

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Electronics");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        Category result = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(categoryId, result.getId(), "The category ID should match");
        assertEquals("Electronics", result.getName(), "The category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testGetCategoryById_NotFound() {
        // Arrange
        String categoryId = "non-existent-id";

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(categoryId));
        assertEquals("Category not found", exception.getMessage(), "Expected 'Category not found' exception message");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
    }
}*/
