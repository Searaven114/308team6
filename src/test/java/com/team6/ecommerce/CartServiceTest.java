package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//public class CartServiceTest {
//
//    @Test
//    public void testFetchUserCart_WhenCartDoesNotExist() {
//        // Arrange
//        String userId = "test-user-id";
//        CartRepository cartRepository = mock(CartRepository.class);
//
//        // Simulate no cart found in repository
//        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
//
//        CartService cartService = new CartService(
//                null, // Mocked UserService
//                null, // Mocked UserRepository
//                null, // Mocked ProductRepository
//                null, // Mocked HttpSession
//                cartRepository,
//                null, // Mocked OrderRepository
//                null, // Mocked InvoiceRepository
//                null  // Mocked PaymentService
//        );
//
//        // Act
//        Cart resultCart = cartService.fetchUserCart(userId);
//
//        // Assert
//        assertNotNull(resultCart, "Cart should not be null");
//        assertEquals(userId, resultCart.getUserId(), "Cart userId should match the provided userId");
//        assertTrue(resultCart.getCartItems().isEmpty(), "Cart should be empty initially");
//        assertEquals(0.0, resultCart.getTotalPrice(), "Total price should be 0.0 for a new cart");
//
//        // Verify a new cart was saved
//        verify(cartRepository, times(1)).save(any(Cart.class));
//    }
//}
