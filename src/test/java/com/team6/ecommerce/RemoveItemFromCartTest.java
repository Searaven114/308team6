package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.delivery.DeliveryListService;
import com.team6.ecommerce.invoice.InvoiceRepository;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.order.OrderRepository;
import com.team6.ecommerce.order.OrderService;
import com.team6.ecommerce.payment.PaymentService;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
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
        OrderService orderService = mock(OrderService.class);        Product mockProduct = new Product();
        mockProduct.setId(productId);

        CartItem cartItem = new CartItem(mockProduct, 2);

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.getCartItems().add(cartItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

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


        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

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
        String result = cartService.removeItemFromUserCart(userId, productId);

        // Assert
        assertEquals(Strings.PRODUCT_NOT_IN_CART, result, "Expected product not in cart message");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should remain empty");

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, never()).save(mockCart);
    }
}