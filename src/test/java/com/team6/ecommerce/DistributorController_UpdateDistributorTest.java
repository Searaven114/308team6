import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorController;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DistributorController.class)
public class DistributorController_UpdateDistributorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorService distributorService;

    @Test
    public void testUpdateDistributor_Success() throws Exception {
        // Arrange
        Distributor updatedDistributor = new Distributor();
        updatedDistributor.setId("1");
        updatedDistributor.setName("Updated Distributor");

        when(distributorService.updateDistributor(eq("1"), any(Distributor.class))).thenReturn(updatedDistributor);

        // Act & Assert
        mockMvc.perform(put("/api/distributors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Distributor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Updated Distributor"));

        verify(distributorService, times(1)).updateDistributor(eq("1"), any(Distributor.class));
    }

    @Test
    public void testUpdateDistributor_NotFound() throws Exception {
        // Arrange
        when(distributorService.updateDistributor(eq("999"), any(Distributor.class)))
                .thenThrow(new RuntimeException("Distributor not found"));

        // Act & Assert
        mockMvc.perform(put("/api/distributors/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Distributor\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Distributor not found"));

        verify(distributorService, times(1)).updateDistributor(eq("999"), any(Distributor.class));
    }
}
