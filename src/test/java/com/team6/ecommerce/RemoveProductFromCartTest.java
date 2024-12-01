package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
public class RemoveProductFromCartTest {

    @Test
    public void testRemoveProductFromCart_WhenProductExists() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";

        CartRepository cartRepository = mock(CartRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);

        CartItem cartItem = new CartItem(mockProduct, 2); // 2 * 100 = 200

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.getCartItems().add(cartItem);
        mockCart.setTotalPrice(200.0);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

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
        String result = cartService.removeItemFromUserCart(userId, productId);

        // Assert
        assertEquals(Strings.PRODUCT_REMOVED_FROM_CART, result, "Expected success message for product removal");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should be empty after product removal");
        assertEquals(0.0, mockCart.getTotalPrice(), "Total price should be updated to 0.0");

        // Verify interactions
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testRemoveProductFromCart_WhenProductDoesNotExist() {
        // Arrange
        String userId = "test-user-id";
        String productId = "non-existent-product-id";

        CartRepository cartRepository = mock(CartRepository.class);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>()); // No items in the cart
        mockCart.setTotalPrice(0.0);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

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
        String result = cartService.removeItemFromUserCart(userId, productId);

        // Assert
        assertEquals(Strings.PRODUCT_NOT_IN_CART, result, "Expected not found message for product removal attempt");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should remain empty");

        // Verify no save was called
        verify(cartRepository, never()).save(mockCart);
    }
}
*/