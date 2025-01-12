package com.team6.ecommerce;

import com.team6.ecommerce.delivery.DeliveryList;
import com.team6.ecommerce.delivery.DeliveryListRepository;
import com.team6.ecommerce.delivery.DeliveryListService;
import com.team6.ecommerce.address.Address;
import com.team6.ecommerce.cartitem.CartItem2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryListServiceTest {

    @InjectMocks
    private DeliveryListService deliveryService;

    @Mock
    private DeliveryListRepository deliveryRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDeliveryEntry() {
        String customerId = "customer123";
        CartItem2 cartItem2 = mock(CartItem2.class);
        Address address = mock(Address.class);
        DeliveryList mockDelivery = new DeliveryList(null, customerId, cartItem2, address, false, new Date());

        when(deliveryRepo.save(any(DeliveryList.class))).thenReturn(mockDelivery);

        DeliveryList result = deliveryService.createDeliveryEntry(customerId, cartItem2, address);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        verify(deliveryRepo, times(1)).save(any(DeliveryList.class));
    }

    @Test
    void testGetDeliveriesByCustomerId() {
        String customerId = "customer123";
        List<DeliveryList> mockDeliveries = List.of(new DeliveryList());

        when(deliveryRepo.findByCustomerId(customerId)).thenReturn(mockDeliveries);

        List<DeliveryList> result = deliveryService.getDeliveriesByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deliveryRepo, times(1)).findByCustomerId(customerId);
    }

    @Test
    void testGetPendingDeliveries() {
        List<DeliveryList> mockDeliveries = List.of(new DeliveryList());

        when(deliveryRepo.findByIsCompleted(false)).thenReturn(mockDeliveries);

        List<DeliveryList> result = deliveryService.getPendingDeliveries();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deliveryRepo, times(1)).findByIsCompleted(false);
    }

    @Test
    void testUpdateDeliveryStatus() {
        String deliveryId = "delivery123";
        DeliveryList mockDelivery = new DeliveryList();
        mockDelivery.setId(deliveryId);
        mockDelivery.setCompleted(false);

        when(deliveryRepo.findById(deliveryId)).thenReturn(Optional.of(mockDelivery));
        when(deliveryRepo.save(mockDelivery)).thenReturn(mockDelivery);

        DeliveryList result = deliveryService.updateDeliveryStatus(deliveryId, true);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        verify(deliveryRepo, times(1)).findById(deliveryId);
        verify(deliveryRepo, times(1)).save(mockDelivery);
    }
    @Test
    void testGetPendingDeliveries_EmptyList() {
        when(deliveryRepo.findByIsCompleted(false)).thenReturn(Collections.emptyList());
    
        List<DeliveryList> result = deliveryService.getPendingDeliveries();
    
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deliveryRepo, times(1)).findByIsCompleted(false);
    }
    @Test
    void testCreateDeliveryEntry_NullInputs() {
        String customerId = "customer123";
    
        Exception exception = assertThrows(NullPointerException.class, () -> {
            deliveryService.createDeliveryEntry(customerId, null, null);
        });
    
        assertNotNull(exception);
    }
    @Test
    void testUpdateDeliveryStatus_UnauthorizedAccess() throws Exception {
        mockMvc.perform(patch("/api/pm/deliveries/delivery123/status")
                        .param("isCompleted", "true"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(username = "productManager", roles = {"PRODUCTMANAGER"})
    void testUpdateDeliveryStatus_InvalidDeliveryId() throws Exception {
        String invalidDeliveryId = "invalidId";
    
        mockMvc.perform(patch("/api/pm/deliveries/" + invalidDeliveryId + "/status")
                        .param("isCompleted", "true"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDeliveryListIntegrity() {
        DeliveryList delivery = new DeliveryList();
        delivery.setId("delivery123");
        delivery.setCustomerId("customer123");
        delivery.setCompleted(true);
    
        assertEquals("delivery123", delivery.getId());
        assertEquals("customer123", delivery.getCustomerId());
        assertTrue(delivery.isCompleted());
    }
    
    @Test
    void testUpdateDeliveryStatus_NotFound() {
        String deliveryId = "delivery123";

        when(deliveryRepo.findById(deliveryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            deliveryService.updateDeliveryStatus(deliveryId, true);
        });

        assertEquals("Delivery not found", exception.getMessage());
        verify(deliveryRepo, times(1)).findById(deliveryId);
    }
}
