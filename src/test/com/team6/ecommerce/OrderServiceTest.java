package com.team6.ecommerce.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testFetchOrderById_ValidId() {
        // Arrange
        String orderId = "validOrderId";
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Act
        Optional<Order> result = orderService.fetchOrderById(orderId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFetchOrderById_InvalidId() {
        // Arrange
        String orderId = "invalidOrderId";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act
        Optional<Order> result = orderService.fetchOrderById(orderId);

        // Assert
        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFetchOrderById_NullId() {
        // Arrange
        String orderId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderService.fetchOrderById(orderId));
        verifyNoInteractions(orderRepository);
    }
}
