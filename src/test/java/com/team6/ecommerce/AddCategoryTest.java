import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
public class AddCategoryTest {

    @Test
    public void testAddCategory_Success() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category newCategory = new Category();
        newCategory.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId("1");
        savedCategory.setName("New Category");

        when(categoryRepository.save(newCategory)).thenReturn(savedCategory);

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        Category result = categoryService.addCategory(newCategory);

        // Assert
        assertNotNull(result, "Saved category should not be null");
        assertEquals("1", result.getId(), "Category ID should match");
        assertEquals("New Category", result.getName(), "Category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).save(newCategory);
    }
}*/
