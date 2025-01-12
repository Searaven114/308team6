package com.team6.ecommerce;

import com.team6.ecommerce.delivery.DeliveryList;
import com.team6.ecommerce.delivery.DeliveryListController;
import com.team6.ecommerce.delivery.DeliveryListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryListControllerTest {

    @InjectMocks
    private DeliveryListController deliveryController;

    @Mock
    private DeliveryListService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPendingDeliveries() {
        List<DeliveryList> mockDeliveries = List.of(new DeliveryList());

        when(deliveryService.getPendingDeliveries()).thenReturn(mockDeliveries);

        ResponseEntity<List<DeliveryList>> response = deliveryController.getPendingDeliveries();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(deliveryService, times(1)).getPendingDeliveries();
    }

    @Test
    void testUpdateDeliveryStatus() {
        String deliveryId = "delivery123";
        DeliveryList mockDelivery = new DeliveryList();
        mockDelivery.setId(deliveryId);
        mockDelivery.setCompleted(true);

        when(deliveryService.updateDeliveryStatus(deliveryId, true)).thenReturn(mockDelivery);

        ResponseEntity<DeliveryList> response = deliveryController.updateDeliveryStatus(deliveryId, true);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isCompleted());
        verify(deliveryService, times(1)).updateDeliveryStatus(deliveryId, true);
    }
}

