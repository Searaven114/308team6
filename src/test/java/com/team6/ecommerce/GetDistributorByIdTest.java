/*import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetDistributorByIdTest {

    @Test
    public void testGetDistributorById_Success() {
        // Arrange
        String distributorId = "1";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        Distributor distributor = new Distributor();
        distributor.setId(distributorId);
        distributor.setName("Distributor One");

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.of(distributor));

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.getDistributorById(distributorId);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(distributorId, result.getId(), "The distributor ID should match");
        assertEquals("Distributor One", result.getName(), "The distributor name should match");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
    }

    @Test
    public void testGetDistributorById_NotFound() {
        // Arrange
        String distributorId = "non-existent-id";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.empty());

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> distributorService.getDistributorById(distributorId));
        assertEquals("Distributor not found", exception.getMessage(), "Expected 'Distributor not found' exception message");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
    }
}*/
