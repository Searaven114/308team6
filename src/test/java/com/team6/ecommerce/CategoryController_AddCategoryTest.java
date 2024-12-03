/*import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryController;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryController_AddCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddCategory_Success() throws Exception {
        // Arrange
        Category newCategory = new Category();
        newCategory.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId("1");
        savedCategory.setName("New Category");

        when(categoryService.addCategory(any(Category.class))).thenReturn(savedCategory);

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Category\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("New Category"));

        verify(categoryService, times(1)).addCategory(any(Category.class));
    }
}*/
