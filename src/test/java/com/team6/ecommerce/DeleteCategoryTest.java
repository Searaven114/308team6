import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteCategoryTest {

    @Test
    public void testDeleteCategory_Success() {
        // Arrange
        String categoryId = "1";

        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Electronics");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        doNothing().when(categoryRepository).deleteById(categoryId);

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        String result = categoryService.deleteCategory(categoryId);

        // Assert
        assertEquals("Category deleted successfully", result, "Expected success message for category deletion");

        // Verify interactions
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
