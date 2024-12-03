import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddDistributorTest {

    @Test
    public void testAddDistributor_Success() {
        // Arrange
        DistributorRepository distributorRepository = mock(DistributorRepository.class);
        Distributor distributor = new Distributor();
        distributor.setName("Test Distributor");

        when(distributorRepository.save(distributor)).thenReturn(distributor);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.save(distributor);

        // Assert
        assertNotNull(result, "The saved distributor should not be null");
        assertEquals("Test Distributor", result.getName(), "The distributor name should match");

        verify(distributorRepository, times(1)).save(distributor);
    }
}
