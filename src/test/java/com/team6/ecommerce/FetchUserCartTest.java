package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.product.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FetchUserCartTest {

    @Test
    public void testFetchUserCart_WhenCartExists() {
        // Arrange
        String userId = "test-user-id";
        CartRepository cartRepository = mock(CartRepository.class);

        CartItem mockCartItem = new CartItem();
        Product mockProduct = new Product();
        mockProduct.setBasePrice(50.0);
        mockCartItem.setProduct(mockProduct);
        mockCartItem.setQuantity(2);

        List<CartItem> mockCartItems = new ArrayList<>();
        mockCartItems.add(mockCartItem);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(mockCartItems);
        mockCart.setTotalPrice(0.0); // Set total price initially as 0 for testing

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));
        CartService cartService = new CartService(
                null, // UserService
                null, // UserRepository
                null, // ProductRepository
                null, // HttpSession
                cartRepository,
                null, // OrderRepository
                null, // InvoiceRepository
                null, // PaymentService
                null, // NotificationService
                null  // InvoiceService
        );

        // Act
        Cart result = cartService.fetchUserCart(userId);

        // Assert
        assertNotNull(result, "Cart should not be null");
        assertEquals(userId, result.getUserId(), "User ID should match");
        assertEquals(100.0, result.getTotalPrice(), "Cart total price should match"); // Updated expected value

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, never()).save(any());
    }
}