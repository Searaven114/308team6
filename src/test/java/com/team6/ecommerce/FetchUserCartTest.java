package com.team6.ecommerce;

import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.cartitem.CartItem;
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
        //CartRepository cartRepository = mock(CartRepository.class);
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