package com.team6.ecommerce.order;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@AllArgsConstructor
@RequestMapping("/api")
@Service
public class OrderController {

    private final OrderService orderService;







/*
    @GetMapping("/orders")
    public List<Order> fetchAllOrdersByUserId(HttpServletRequest request) {
        return this.customerService.fetchAllOrdersByUserId(request);
    }


    @PostMapping("/order/create")
    public OrderResponse createOrder(HttpServletRequest request) {
        return this.customerService.createOrder(request);
    }
*/


}
