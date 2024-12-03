/*import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorController;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;*/

/*@WebMvcTest(DistributorController.class)
public class DistributorController_AddDistributorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistributorService distributorService;

    @Test
    public void testAddDistributor_Success() throws Exception {
        // Arrange
        Distributor distributor = new Distributor();
        distributor.setId("1");
        distributor.setName("New Distributor");

        when(distributorService.addDistributor(any(Distributor.class))).thenReturn(distributor);

        // Act & Assert
        mockMvc.perform(post("/api/distributors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Distributor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("New Distributor"));

        verify(distributorService, times(1)).addDistributor(any(Distributor.class));
    }
}*/
