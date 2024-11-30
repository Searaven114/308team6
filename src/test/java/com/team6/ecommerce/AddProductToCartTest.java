package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddProductToCartTest {

    @Test
    public void testAddItemToUserCart_WhenStockIsSufficient() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";
        int quantityToAdd = 2;

        CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(10);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.setTotalPrice(0.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                null, // Mocked UserRepository
                productRepository,
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null  // Mocked PaymentService
        );

        // Act
        String result = cartService.addItemToUserCart(userId, productId, quantityToAdd);

        // Assert
        assertEquals(Strings.PRODUCT_ADDED_TO_CART, result, "Expected success message for product addition");
        assertEquals(1, mockCart.getCartItems().size(), "Cart should contain 1 item");
        assertEquals(200.0, mockCart.getTotalPrice(), "Cart total price should be updated correctly");

        CartItem addedItem = mockCart.getCartItems().get(0);
        assertEquals(productId, addedItem.getProduct().getId(), "Product ID should match");
        assertEquals(quantityToAdd, addedItem.getQuantity(), "Product quantity should match");

        // Verify interactions
        verify(cartRepository, times(1)).save(mockCart);
    }
}
