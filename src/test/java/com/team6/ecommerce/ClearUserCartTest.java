package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.delivery.DeliveryListService;
import com.team6.ecommerce.invoice.InvoiceRepository;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.order.OrderRepository;
import com.team6.ecommerce.order.OrderService;
import com.team6.ecommerce.payment.PaymentService;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClearUserCartTest {

    @Test
    public void testClearUserCart_Success() {
        // Arrange
        String userId = "test-user-id";

        CartRepository cartRepository = mock(CartRepository.class);
        UserRepository userRepository = mock(UserRepository.class); // Mock UserRepository
        //CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        UserService userService = mock(UserService.class);
        //UserRepository userRepository = mock(UserRepository.class);
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
        mockCart.setTotalPrice(200.0); // Initially has items
        mockCart.setCartItems(new ArrayList<>());

        User mockUser = new User(); // Create a mock User instance
        mockUser.setId(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser)); // Return a valid User

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
        String result = cartService.clearUserCart(userId);

        // Assert
        assertEquals(Strings.CART_HAS_BEEN_CLEARED, result, "Cart should be cleared successfully");
        assertEquals(0.0, mockCart.getTotalPrice(), "Cart total price should be reset to 0");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart items should be empty");

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(mockCart);
    }

    @Test
    public void testClearUserCart_WhenCartIsEmpty() {
        // Arrange
        String userId = "test-user-id";

        CartRepository cartRepository = mock(CartRepository.class);
        UserRepository userRepository = mock(UserRepository.class); // Mock UserRepository
        //CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        UserService userService = mock(UserService.class);
        //UserRepository userRepository = mock(UserRepository.class);
        HttpSession session = mock(HttpSession.class);
        OrderRepository orderRepository = mock(OrderRepository.class);
        InvoiceRepository invoiceRepository = mock(InvoiceRepository.class);
        PaymentService paymentService = mock(PaymentService.class);
        NotificationService notificationService = mock(NotificationService.class);
        InvoiceService invoiceService = mock(InvoiceService.class);
        DeliveryListService deliveryListService = mock(DeliveryListService.class);
        OrderService orderService = mock(OrderService.class);

        User mockUser = new User(); // Create a mock User instance
        mockUser.setId(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser)); // Return a valid User

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
        String result = cartService.clearUserCart(userId);

        // Assert
        assertEquals(Strings.CART_IS_EMPTY, result, "Should return CART_IS_EMPTY message");

        // Verify interactions
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, never()).save(any());
    }
}