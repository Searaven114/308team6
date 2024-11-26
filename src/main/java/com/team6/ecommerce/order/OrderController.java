package com.team6.ecommerce.order;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@RequestMapping("/api")
@Service
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepo;

    /**
     * Fetch orders by User ID
     *
     * @param userId The User ID
     * @return List of orders for the specified user
     */
    public List<Order> fetchOrdersByUserId(String userId) {
        log.info("[OrderService][fetchOrdersByUserId] Fetching orders for User ID: {}", userId);
        return orderRepo.findAllByUserId(userId);
    }

    /**
     * Fetch order by Order ID
     *
     * @param orderId The Order ID
     * @return The order if found, otherwise Optional.empty()
     */
    public Optional<Order> fetchOrderById(String orderId) {
        log.info("[OrderService][fetchOrderById] Fetching order with ID: {}", orderId);
        return orderRepo.findById(orderId);
    }

    /**
     * Fetch orders by Order Status
     *
     * @param status The Order Status
     * @return List of orders with the specified status
     */
    public List<Order> fetchOrdersByStatus(OrderStatus status) {
        log.info("[OrderService][fetchOrdersByStatus] Fetching orders with status: {}", status);
        return orderRepo.findAllByOrderStatus(status);
    }







}
