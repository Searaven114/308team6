package com.team6.ecommerce;

import com.team6.ecommerce.distributor.Distributor;
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
        distributor.setName("Test Distributor");

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.of(distributor));

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.findById(distributorId).orElse(null);

        // Assert
        assertNotNull(result, "Distributor should not be null");
        assertEquals("Test Distributor", result.getName(), "The distributor name should match");

        verify(distributorRepository, times(1)).findById(distributorId);
    }

    @Test
    public void testGetDistributorById_NotFound() {
        // Arrange
        String distributorId = "999";
        DistributorRepository distributorRepository = mock(DistributorRepository.class);

        when(distributorRepository.findById(distributorId)).thenReturn(Optional.empty());

        DistributorService distributorService = new DistributorService(distributorRepository);

        // Act
        Distributor result = distributorService.findById(distributorId).orElse(null);

        // Assert
        assertNull(result, "Distributor should be null when not found");

        verify(distributorRepository, times(1)).findById(distributorId);
    }
}
