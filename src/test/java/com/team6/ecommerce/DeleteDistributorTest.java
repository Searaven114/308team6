import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteDistributorTest {

    @Test
    public void testDeleteDistributor_Success() {
        // Arrange
        String distributorId = "1";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        Distributor existingDistributor = new Distributor();
        existingDistributor.setId(distributorId);
        existingDistributor.setName("Distributor to Delete");

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.of(existingDistributor));
        doNothing().when(distributorRepository).deleteById(distributorId);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        String result = distributorService.deleteDistributor(distributorId);

        // Assert
        assertEquals("Distributor deleted successfully", result, "Expected success message for distributor deletion");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
        verify(distributorRepository, times(1)).deleteById(distributorId);
    }

    @Test
    public void testDeleteDistributor_NotFound() {
        // Arrange
        String distributorId = "non-existent-id";

        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.empty());

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> distributorService.deleteDistributor(distributorId));
        assertEquals("Distributor not found", exception.getMessage(), "Expected 'Distributor not found' exception message");

        // Verify interactions
        verify(distributorRepository, times(1)).findById(distributorId);
        verify(distributorRepository, never()).deleteById(distributorId);
    }
}
