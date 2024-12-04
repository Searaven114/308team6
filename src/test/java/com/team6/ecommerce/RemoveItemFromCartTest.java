package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RemoveItemFromCartTest {

    @Test
    public void testRemoveItemFromCart_Success() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";

        CartRepository cartRepository = mock(CartRepository.class);
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        CartItem cartItem = new CartItem(mockProduct, 2);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.getCartItems().add(cartItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                null, // Mocked UserRepository
                null, // Mocked ProductRepository
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null, // Mocked PaymentService
                null, // Mocked NotificationService
                null  // Mocked InvoiceService
        );

        // Act
        String result = cartService.removeItemFromUserCart(userId, productId);

        // Assert
        assertEquals(Strings.PRODUCT_REMOVED_FROM_CART, result, "Expected product removal success message");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should be empty after item removal");

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testRemoveItemFromCart_ProductNotFound() {
        // Arrange
        String userId = "test-user-id";
        String productId = "non-existent-product-id";

        CartRepository cartRepository = mock(CartRepository.class);
        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                null, // Mocked UserRepository
                null, // Mocked ProductRepository
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null, // Mocked PaymentService
                null, // Mocked NotificationService
                null  // Mocked InvoiceService
        );

        // Act
        String result = cartService.removeItemFromUserCart(userId, productId);

        // Assert
        assertEquals(Strings.PRODUCT_NOT_IN_CART, result, "Expected product not in cart message");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should remain empty");

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, never()).save(mockCart);
    }
}
