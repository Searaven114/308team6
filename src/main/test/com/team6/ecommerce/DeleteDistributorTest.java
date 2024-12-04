import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DeleteDistributorTest {

    @Test
    public void testDeleteDistributor_Success() {
        // Arrange
        String distributorId = "1";
        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        distributorService.deleteById(distributorId);

        // Assert
        verify(distributorRepository, times(1)).deleteById(distributorId);
    }
}
