package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddItemToUserCartTest {

    @Test
    public void testAddItemToUserCart_WhenStockIsSufficient() {
        // Arrange
        String userId = "test-user-id";
        String productId = "product-1";
        int quantity = 2;

        CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(50.0);
        mockProduct.setQuantityInStock(10);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.setTotalPrice(0.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // UserService
                null, // UserRepository
                productRepository,
                null, // HttpSession
                cartRepository,
                null, // OrderRepository
                null, // InvoiceRepository
                null, // PaymentService
                null, // NotificationService
                null  // InvoiceService
        );

        // Act
        String result = cartService.addItemToUserCart(userId, productId, quantity);

        // Assert
        assertEquals(Strings.PRODUCT_ADDED_TO_CART, result, "Product should be added to cart successfully");
        assertEquals(1, mockCart.getCartItems().size(), "Cart should have one item");
        assertEquals(100.0, mockCart.getTotalPrice(), "Cart total price should be updated");

        CartItem addedItem = mockCart.getCartItems().get(0);
        assertEquals(productId, addedItem.getProduct().getId(), "Product ID should match");
        assertEquals(quantity, addedItem.getQuantity(), "Quantity should match");

        // Verify interactions
        verify(productRepository, times(1)).findById(productId);
        verify(cartRepository, times(1)).save(mockCart);
    }
}
