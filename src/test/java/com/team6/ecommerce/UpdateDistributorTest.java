import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateDistributorTest {

    @Test
    public void testUpdateDistributor_Success() {
        // Arrange
        String distributorId = "1";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        Distributor existingDistributor = new Distributor();
        existingDistributor.setId(distributorId);
        existingDistributor.setName("Old Distributor");

        Distributor updatedDistributor = new Distributor();
        updatedDistributor.setId(distributorId);
        updatedDistributor.setName("Updated Distributor");

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.of(existingDistributor));
        when(distributorRepository.save(existingDistributor)).thenReturn(updatedDistributor);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.updateDistributor(distributorId, updatedDistributor);

        // Assert
        assertNotNull(result, "Updated distributor should not be null");
        assertEquals(distributorId, result.getId(), "Distributor ID should match");
        assertEquals("Updated Distributor", result.getName(), "Distributor name should match");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
        verify(distributorRepository, times(1)).save(existingDistributor);
    }

    @Test
    public void testUpdateDistributor_NotFound() {
        // Arrange
        String distributorId = "non-existent-id";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.empty());

        DistributorService distributorService = new DistributorService(distributorRepository);

        Distributor updatedDistributor = new Distributor();
        updatedDistributor.setId(distributorId);
        updatedDistributor.setName("Updated Distributor");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> distributorService.updateDistributor(distributorId, updatedDistributor));
        assertEquals("Distributor not found", exception.getMessage(), "Expected 'Distributor not found' exception message");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
        verify(distributorRepository, never()).save(any());
    }
}
