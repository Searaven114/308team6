/*import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryController;
import com.team6.ecommerce.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryController_GetAllCategoriesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testGetAllCategories() throws Exception {
        // Arrange
        Category category1 = new Category();
        category1.setId("1");
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId("2");
        category2.setName("Books");

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category1, category2));

        // Act & Assert
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[1].name").value("Books"));

        verify(categoryService, times(1)).getAllCategories();
    }
}*/
