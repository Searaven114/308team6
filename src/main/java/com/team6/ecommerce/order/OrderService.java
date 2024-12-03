package com.team6.ecommerce.order;



import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class OrderService {


    private final OrderRepository orderRepo;
    private final UserService userService;


    @Secured({"ROLE_ADMIN"})
    @PreAuthorize("isAuthenticated()")
    public List<Order> getAllOrdersAdmin( OrderStatus orderStatus){
        return orderRepo.findAllByOrderStatus( orderStatus );
    }


    @Secured({"ROLE_ADMIN"})
    @PreAuthorize("isAuthenticated()")
    public List<Order> getAllOrdersByUserIdAdmin(String userId, OrderStatus orderStatus){

        if ( orderStatus == null){
            return orderRepo.findAll();
        } else {
            return orderRepo.findAllByUserIdAndOrderStatus(userId, orderStatus);
        }
    }


    /**
     * Fetch an order by its ID.
     *
     * @param orderId The order ID
     * @return The order if found
     */
    public Optional<Order> fetchOrderById(String orderId) {
        log.info("[OrderService][fetchOrderById] Fetching order with ID: {}", orderId);

        if (orderId == null || orderId.isEmpty()) {
            log.error("[OrderService][fetchOrderById] Invalid order ID provided.");
            throw new IllegalArgumentException("Order ID cannot be null or empty.");
        }

        Optional<Order> order = orderRepo.findById(orderId);

        if (order.isEmpty()) {
            log.info("[OrderService][fetchOrderById] No order found with ID: {}", orderId);
        } else {
            log.info("[OrderService][fetchOrderById] Order found with ID: {}", orderId);
        }

        return order;
    }


    /**
     * Fetch all orders for a specific user ID.
     *
     * @param userId The user ID
     * @return List of orders for the user
     */
    public List<Order> fetchOrdersByUserId(String userId) {

        log.info("[OrderService][fetchOrdersByUserId] Fetching orders for User ID: {}", userId);

        if (userId == null || userId.isEmpty()) {
            log.error("[OrderService][fetchOrdersByUserId] Invalid user ID provided.");
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }

        List<Order> orders = orderRepo.findAllByUserId(userId);

        log.info("[OrderService][fetchOrdersByUserId] Retrieved {} orders for User ID: {}", orders.size(), userId);
        return orders;
    }


    /**
     * Fetch all orders by their status.
     *
     * @param status The order status
     * @return List of orders with the specified status
     */
    public List<Order> fetchOrdersByStatus(OrderStatus status) {
        log.info("[OrderService][fetchOrdersByStatus] Fetching orders with status: {}", status);

        List<Order> orders = orderRepo.findAllByOrderStatus(status);

        log.info("[OrderService][fetchOrdersByStatus] Retrieved {} orders with status: {}", orders.size(), status);
        return orders;
    }



    @Scheduled(fixedRate = 15000)
    public void simulateOrderStatus() {
        log.info("[OrderService][simulateOrderStatus] Starting order status simulation...");

        // Fetch all orders with status 'PROCESSING'
        List<Order> processingOrders = orderRepo.findAllByOrderStatus(OrderStatus.PROCESSING);

        for (Order order : processingOrders) {
            log.info("[OrderService][simulateOrderStatus] Updating order ID: {} from PROCESSING to IN_TRANSIT", order.getId());

            // Update the status
            order.setOrderStatus(OrderStatus.IN_TRANSIT);

            // Save the updated order
            orderRepo.save(order);

            log.info("[OrderService][simulateOrderStatus] Order ID: {} successfully updated to IN_TRANSIT", order.getId());
        }

        log.info("[OrderService][simulateOrderStatus] Simulation completed.");
    }





    @Scheduled(fixedRate = 23000)
    public void simulateDeliveryStatus() {
        log.info("[OrderService][simulateDeliveryStatus] Starting delivery status simulation...");

        // Fetch all orders with status 'IN_TRANSIT'
        List<Order> inTransitOrders = orderRepo.findAllByOrderStatus(OrderStatus.IN_TRANSIT);

        for (Order order : inTransitOrders) {
            log.info("[OrderService][simulateDeliveryStatus] Updating order ID: {} from IN_TRANSIT to DELIVERED", order.getId());

            // Update the status
            order.setOrderStatus(OrderStatus.DELIVERED);

            // Save the updated order
            orderRepo.save(order);

            log.info("[OrderService][simulateDeliveryStatus] Order ID: {} successfully updated to DELIVERED", order.getId());
        }

        log.info("[OrderService][simulateDeliveryStatus] Delivery simulation completed.");
    }
}























