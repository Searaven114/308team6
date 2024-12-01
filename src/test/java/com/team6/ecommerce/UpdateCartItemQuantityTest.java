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
public class UpdateCartItemQuantityTest {

    @Test
    public void testUpdateCartItemQuantity_WhenQuantityIncreased() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";
        int newQuantity = 5;

        CartRepository cartRepository = mock(CartRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(10);

        CartItem cartItem = new CartItem(mockProduct, 2); // Initial quantity is 2

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
                null  // Mocked PaymentService
        );

        // Act
        String result = cartService.updateCartItemQuantity(userId, productId, newQuantity);

        // Assert
        assertEquals(Strings.CART_UPDATED_SUCCESSFULLY, result, "Expected success message for quantity update");
        assertEquals(newQuantity, cartItem.getQuantity(), "Product quantity should be updated");
        assertEquals(500.0, mockCart.getTotalPrice(), "Total price should be updated correctly");

        // Verify interactions
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testUpdateCartItemQuantity_WhenQuantityDecreasedToZero() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";
        int newQuantity = 0;

        CartRepository cartRepository = mock(CartRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(10);

        CartItem cartItem = new CartItem(mockProduct, 2); // Initial quantity is 2

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
                null  // Mocked PaymentService
        );

        // Act
        String result = cartService.updateCartItemQuantity(userId, productId, newQuantity);

        // Assert
        assertEquals(Strings.CART_UPDATED_SUCCESSFULLY, result, "Expected success message for quantity update");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart item should be removed when quantity is 0");
        assertEquals(0.0, mockCart.getTotalPrice(), "Total price should be updated to 0.0");

        // Verify interactions
        verify(cartRepository, times(1)).save(mockCart);
    }
}*/
