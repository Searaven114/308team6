package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.delivery.DeliveryListService;
import com.team6.ecommerce.order.OrderService;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.order.OrderRepository;
import com.team6.ecommerce.payment.PaymentService;
import com.team6.ecommerce.invoice.InvoiceRepository;

import jakarta.servlet.http.HttpSession;

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

        // Mock dependencies
        CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        UserService userService = mock(UserService.class);
        UserRepository userRepository = mock(UserRepository.class);
        HttpSession session = mock(HttpSession.class);
        OrderRepository orderRepository = mock(OrderRepository.class);
        InvoiceRepository invoiceRepository = mock(InvoiceRepository.class);
        PaymentService paymentService = mock(PaymentService.class);
        NotificationService notificationService = mock(NotificationService.class);
        InvoiceService invoiceService = mock(InvoiceService.class);
        DeliveryListService deliveryListService = mock(DeliveryListService.class);
        OrderService orderService = mock(OrderService.class);


        // Mock Product
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(10);

        // Mock Cart
        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.setTotalPrice(0.0);

        // Mock repository interactions
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        // Create CartService instance
        CartService cartService = new CartService(
                //userService,
                userRepository,
                productRepository,
                //session,
                cartRepository,
                paymentService,
                notificationService,
                invoiceService,
                deliveryListService,
                orderService




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