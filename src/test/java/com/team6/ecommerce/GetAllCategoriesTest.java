/*import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllCategoriesTest {

    @Test
    public void testGetAllCategories() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        Category category1 = new Category();
        category1.setId("1");
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId("2");
        category2.setName("Books");

        List<Category> categories = Stream.of(category1, category2).collect(Collectors.toList());

        when(categoryRepository.findAll()).thenReturn(categories);

        CategoryService categoryService = new CategoryService(categoryRepository);

        // Act
        List<Category> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The size of categories list should be 2");
        assertEquals("Electronics", result.get(0).getName(), "The first category name should match");
        assertEquals("Books", result.get(1).getName(), "The second category name should match");

        // Verify interactions
        verify(categoryRepository, times(1)).findAll();
    }
}
*/