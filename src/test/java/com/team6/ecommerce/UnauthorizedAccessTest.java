import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UnauthorizedAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthorizedAccess_WhenNoAuthentication() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/protected-resource"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Unauthorized"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUnauthorizedAccess_WhenRoleIsInsufficient() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin-resource"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"));
    }
}
