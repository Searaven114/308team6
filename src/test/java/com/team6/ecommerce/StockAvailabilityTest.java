package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.payment.dto.PaymentRequestDTO;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
public class StockAvailabilityTest {

    @Test
    public void testCheckout_WhenStockIsInsufficient() {
        // Arrange
        String userId = "test-user-id";

        UserRepository userRepository = mock(UserRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CartRepository cartRepository = mock(CartRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId("product-1");
        mockProduct.setTitle("Test Product");
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(1); // Stock is less than required

        CartItem cartItem = new CartItem(mockProduct, 2); // Trying to buy 2 items

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.getCartItems().add(cartItem);
        mockCart.setTotalPrice(200.0);

        when(productRepository.findById(mockProduct.getId())).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                userRepository,
                productRepository,
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null  // Mocked PaymentService
        );

        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setCardExpiry("12/23");
        dto.setCardNumber("1234567890123456");
        dto.setCvv("123");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.checkout(userId,dto));
        assertEquals("Not enough stock for product: Test Product", exception.getMessage(), "Expected stock error message");
    }
}*/