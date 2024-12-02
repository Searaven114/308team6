import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryController;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryController_GetCategoryByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetCategoryById_Success() throws Exception {
        // Arrange
        Category category = new Category();
        category.setId("1");
        category.setName("Electronics");

        when(categoryService.getCategoryById("1")).thenReturn(category);

        // Act & Assert
        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));

        verify(categoryService, times(1)).getCategoryById("1");
    }

    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryById("999")).thenThrow(new RuntimeException("Category not found"));

        // Act & Assert
        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found"));

        verify(categoryService, times(1)).getCategoryById("999");
    }
}

