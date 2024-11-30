import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class AuthorizationRolesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    public void testAccessDenied_ForUserRole() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin-resource"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAccessGranted_ForAdminRole() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/admin-resource"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAccessGranted_ForAdminAndUserResource() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/user-resource"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/admin-resource"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAccessGranted_ForUserResource() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/user-resource"))
                .andExpect(status().isOk());
    }
}
