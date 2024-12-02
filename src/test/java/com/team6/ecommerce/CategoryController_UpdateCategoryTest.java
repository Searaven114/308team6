import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryController;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryController_UpdateCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testUpdateCategory_Success() throws Exception {
        // Arrange
        Category updatedCategory = new Category();
        updatedCategory.setId("1");
        updatedCategory.setName("Updated Category");

        when(categoryService.updateCategory(eq("1"), any(Category.class))).thenReturn(updatedCategory);

        // Act & Assert
        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Updated Category"));

        verify(categoryService, times(1)).updateCategory(eq("1"), any(Category.class));
    }

    @Test
    public void testUpdateCategory_NotFound() throws Exception {
        // Arrange
        when(categoryService.updateCategory(eq("999"), any(Category.class)))
                .thenThrow(new RuntimeException("Category not found"));

        // Act & Assert
        mockMvc.perform(put("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Category\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found"));

        verify(categoryService, times(1)).updateCategory(eq("999"), any(Category.class));
    }
}

