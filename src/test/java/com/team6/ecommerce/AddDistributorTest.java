import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
public class AddDistributorTest {

    @Test
    public void testAddDistributor_Success() {
        // Arrange
        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        Distributor newDistributor = new Distributor();
        newDistributor.setName("New Distributor");

        Distributor savedDistributor = new Distributor();
        savedDistributor.setId("1");
        savedDistributor.setName("New Distributor");

        when(distributorRepository.save(newDistributor)).thenReturn(savedDistributor);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.addDistributor(newDistributor);

        // Assert
        assertNotNull(result, "Saved distributor should not be null");
        assertEquals("1", result.getId(), "Distributor ID should match");
        assertEquals("New Distributor", result.getName(), "Distributor name should match");

        // Verify interactions
        verify(distributorRepository, times(1)).save(newDistributor);
    }
}*/
