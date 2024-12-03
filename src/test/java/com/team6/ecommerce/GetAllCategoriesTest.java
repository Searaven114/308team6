import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllCategoriesTest {

    @Test
    public void testFindAllCategories() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category category1 = new Category();
        category1.setId("1");
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId("2");
        category2.setName("Books");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        List<Category> categories = categoryService.findAll();

        // Assert
        assertNotNull(categories, "Categories list should not be null");
        assertEquals(2, categories.size(), "Categories list size should match");
        assertEquals("Electronics", categories.get(0).getName());
        assertEquals("Books", categories.get(1).getName());

        // Verify interactions
        verify(categoryRepository, times(1)).findAll();
    }
}
