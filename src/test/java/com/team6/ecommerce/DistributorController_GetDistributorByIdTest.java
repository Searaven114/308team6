import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorController;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DistributorController.class)
public class DistributorController_GetDistributorByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorService distributorService;

    @Test
    public void testGetDistributorById_Success() throws Exception {
        // Arrange
        Distributor distributor = new Distributor();
        distributor.setId("1");
        distributor.setName("Distributor One");

        when(distributorService.getDistributorById("1")).thenReturn(distributor);

        // Act & Assert
        mockMvc.perform(get("/api/distributors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Distributor One"));

        verify(distributorService, times(1)).getDistributorById("1");
    }

    @Test
    public void testGetDistributorById_NotFound() throws Exception {
        // Arrange
        when(distributorService.getDistributorById("999")).thenThrow(new RuntimeException("Distributor not found"));

        // Act & Assert
        mockMvc.perform(get("/api/distributors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Distributor not found"));

        verify(distributorService, times(1)).getDistributorById("999");
    }
}
