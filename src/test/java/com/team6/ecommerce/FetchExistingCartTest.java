package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
public class FetchExistingCartTest {

    @Test
    public void testFetchUserCart_WhenCartExists() {
        // Arrange
        String userId = "existing-user-id";
        CartRepository cartRepository = mock(CartRepository.class);

        // Mock existing cart
        Cart existingCart = new Cart();
        existingCart.setUserId(userId);
        existingCart.setCartItems(new ArrayList<>());
        existingCart.setTotalPrice(50.0);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                null, // Mocked UserRepository
                null, // Mocked ProductRepository
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null  // Mocked PaymentService
        );

        // Act
        Cart resultCart = cartService.fetchUserCart(userId);

        // Assert
        assertNotNull(resultCart, "Cart should not be null");
        assertEquals(userId, resultCart.getUserId(), "Cart userId should match the provided userId");
        assertEquals(50.0, resultCart.getTotalPrice(), "Total price should match the existing cart's total price");

        // Verify no new cart was saved
        verify(cartRepository, never()).save(any(Cart.class));
    }
}
*/