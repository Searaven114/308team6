/*import com.team6.ecommerce.category.CategoryController;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryController_DeleteCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testDeleteCategory_Success() throws Exception {
        // Arrange
        doNothing().when(categoryService).deleteCategory("1");

        // Act & Assert
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Category deleted successfully"));

        verify(categoryService, times(1)).deleteCategory("1");
    }

    @Test
    public void testDeleteCategory_NotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Category not found")).when(categoryService).deleteCategory("999");

        // Act & Assert
        mockMvc.perform(delete("/api/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found"));

        verify(categoryService, times(1)).deleteCategory("999");
    }
}*/
