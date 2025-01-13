package com.team6.ecommerce.order;

import com.team6.ecommerce.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepo;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/orders")
    public ResponseEntity<?> getOrdersByUser() {

        String userId = getAuthenticatedUserId("getOrdersByUser");

        //log.info("[OrderController][getOrdersByUser] Fetching orders for userId: {}", userId);

        // Fetch orders for the user
        List<Order> orders = orderService.fetchOrdersByUserId(userId);

        if (orders.isEmpty()) {
            log.info("[OrderController][getOrdersByUser] No orders found for userId: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the given user ID.");
        }

        //log.info("[OrderController][getOrdersByUser] Found {} orders for userId: {}", orders.size(), userId);
        return ResponseEntity.ok(orders);
    }


    /**
     * Endpoint to fetch an order by its ID.
     *
     * @param orderId The Order ID
     * @return The order if found, otherwise NOT FOUND response
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        //log.info("[OrderController][getOrderById] Fetching order with ID: {}", orderId);

        // Validate orderId
        if (orderId == null || orderId.trim().isEmpty()) {
            log.error("[OrderController][getOrderById] Invalid orderId provided.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId provided.");
        }

        // Fetch the order
        Optional<Order> order = orderService.fetchOrderById(orderId);

        if (order.isEmpty()) {
            log.info("[OrderController][getOrderById] No order found with ID: {}", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

        //log.info("[OrderController][getOrderById] Retrieved order with ID: {}", orderId);
        return ResponseEntity.ok(order.get());
    }



    /**
     * Endpoint to fetch orders by their status.
     *
     * @param status The Order Status
     * @return List of orders with the specified status
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable OrderStatus status) {
        log.info("[OrderController][getOrdersByStatus] Fetching orders with status: {}", status);

        // Fetch orders by status
        List<Order> orders = orderService.fetchOrdersByStatus(status);

        if (orders.isEmpty()) {
            log.info("[OrderController][getOrdersByStatus] No orders found with status: {}", status);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found with the specified status.");
        }

        log.info("[OrderController][getOrdersByStatus] Retrieved {} orders with status: {}", orders.size(), status);
        return ResponseEntity.ok(orders);
    }


    /**
     * Private helper to retrieve the authenticated user's ID with method name for logging.
     */
    private String getAuthenticatedUserId(String methodName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("[OrderController][{}] Unauthorized access attempt.", methodName);
            throw new IllegalStateException("User is not authenticated.");
        }
        if (!(authentication.getPrincipal() instanceof User)) {
            log.warn("[OrderController][{}] Invalid principal type.", methodName);
            throw new IllegalStateException("Invalid user principal.");
        }
        User user = (User) authentication.getPrincipal();
        log.info("[OrderController][{}] Authenticated user ID: {}", methodName, user.getId());
        return user.getId();
    }


    @PreAuthorize("hasRole('PRODUCT_MANAGER')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestParam int statusCode) {
        log.info("[OrderController][updateOrderStatus] Request to update status of order ID: {} with statusCode: {}", orderId, statusCode);

        // Validate orderId
        if (orderId == null || orderId.trim().isEmpty()) {
            log.error("[OrderController][updateOrderStatus] Invalid orderId provided.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order ID provided.");
        }

        // Map the statusCode to OrderStatus
        OrderStatus newStatus;
        switch (statusCode) {
            case 1:
                newStatus = OrderStatus.IN_TRANSIT;
                break;
            case 2:
                newStatus = OrderStatus.PROCESSING;
                break;
            case 3:
                newStatus = OrderStatus.DELIVERED;
                break;
            case 4:
                newStatus = OrderStatus.REFUNDED;
                break;
            default:
                log.error("[OrderController][updateOrderStatus] Invalid statusCode provided: {}", statusCode);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status code. Accepted values are 1 (In Transit), 2 (Processing), 3 (Delivered), 4 (Refunded).");
        }

        // Fetch the order
        Optional<Order> orderOpt = orderRepo.findById(orderId);
        if (orderOpt.isEmpty()) {
            log.info("[OrderController][updateOrderStatus] No order found with ID: {}", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

        // Update the status
        Order order = orderOpt.get();
        order.setOrderStatus(newStatus);
        orderRepo.save(order);

        log.info("[OrderController][updateOrderStatus] Updated status of order ID: {} to {}", orderId, newStatus);
        return ResponseEntity.ok("Order status updated successfully to: " + newStatus);
    }


}
