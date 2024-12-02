import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.distributor.DistributorService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllDistributorsTest {

    @Test
    public void testGetAllDistributors() {
        // Arrange
        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        Distributor distributor1 = new Distributor();
        distributor1.setId("1");
        distributor1.setName("Distributor One");

        Distributor distributor2 = new Distributor();
        distributor2.setId("2");
        distributor2.setName("Distributor Two");

        List<Distributor> distributors = Stream.of(distributor1, distributor2).collect(Collectors.toList());

        when(distributorRepository.findAll()).thenReturn(distributors);

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        List<Distributor> result = distributorService.getAllDistributors();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The size of distributors list should be 2");
        assertEquals("Distributor One", result.get(0).getName(), "The first distributor name should match");
        assertEquals("Distributor Two", result.get(1).getName(), "The second distributor name should match");

        // Verify interactions
        verify(distributorRepository, times(1)).findAll();
    }
}
