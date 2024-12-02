import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorController;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DistributorController.class)
public class DistributorController_GetAllDistributorsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorService distributorService;

    @Test
    public void testGetAllDistributors() throws Exception {
        // Arrange
        Distributor distributor1 = new Distributor();
        distributor1.setId("1");
        distributor1.setName("Distributor One");

        Distributor distributor2 = new Distributor();
        distributor2.setId("2");
        distributor2.setName("Distributor Two");

        when(distributorService.getAllDistributors()).thenReturn(Arrays.asList(distributor1, distributor2));

        // Act & Assert
        mockMvc.perform(get("/api/distributors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Distributor One"))
                .andExpect(jsonPath("$[1].name").value("Distributor Two"));

        verify(distributorService, times(1)).getAllDistributors();
    }
}
