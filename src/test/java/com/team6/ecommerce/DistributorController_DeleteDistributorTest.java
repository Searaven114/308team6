/*import com.team6.ecommerce.distributor.DistributorController;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DistributorController.class)
public class DistributorController_DeleteDistributorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorService distributorService;

    @Test
    public void testDeleteDistributor_Success() throws Exception {
        // Arrange
        doNothing().when(distributorService).deleteDistributor("1");

        // Act & Assert
        mockMvc.perform(delete("/api/distributors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Distributor deleted successfully"));

        verify(distributorService, times(1)).deleteDistributor("1");
    }

    @Test
    public void testDeleteDistributor_NotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Distributor not found")).when(distributorService).deleteDistributor("999");

        // Act & Assert
        mockMvc.perform(delete("/api/distributors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Distributor not found"));

        verify(distributorService, times(1)).deleteDistributor("999");
    }
}*/
