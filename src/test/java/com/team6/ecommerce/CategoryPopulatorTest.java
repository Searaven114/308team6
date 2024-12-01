import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.populator.CategoryPopulator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CategoryPopulatorTest {

    @Test
    public void testPopulateCategories_WhenCategoriesNotExist() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        when(categoryRepository.findByName("Fashion")).thenReturn(Optional.empty());

        CategoryPopulator categoryPopulator = new CategoryPopulator(categoryRepository);

        // Act
        categoryPopulator.populate();

        // Assert
        verify(categoryRepository, times(1)).save(new Category("Electronics"));
        verify(categoryRepository, times(1)).save(new Category("Fashion"));
    }

    @Test
    public void testPopulateCategories_WhenCategoriesAlreadyExist() {
        // Arrange
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(new Category("Electronics")));
        when(categoryRepository.findByName("Fashion")).thenReturn(Optional.of(new Category("Fashion")));

        CategoryPopulator categoryPopulator = new CategoryPopulator(categoryRepository);

        // Act
        categoryPopulator.populate();

        // Assert
        verify(categoryRepository, never()).save(any());
    }
}
