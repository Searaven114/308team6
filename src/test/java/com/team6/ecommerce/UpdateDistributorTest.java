import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateDistributorTest {

    @Test
    public void testUpdateDistributor() {
        // Arrange
        DistributorRepository distributorRepository = mock(DistributorRepository.class);
        Distributor existingDistributor = new Distributor();
        existingDistributor.setId("1");
        existingDistributor.setName("Old Name");

        Distributor updatedDistributor = new Distributor();
        updatedDistributor.setId("1");
        updatedDistributor.setName("Updated Name");

        when(distributorRepository.save(updatedDistributor)).thenReturn(updatedDistributor);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.save(updatedDistributor);

        // Assert
        assertNotNull(result, "Updated distributor should not be null");
        assertEquals("Updated Name", result.getName(), "The distributor name should be updated");

        verify(distributorRepository, times(1)).save(updatedDistributor);
    }
}
