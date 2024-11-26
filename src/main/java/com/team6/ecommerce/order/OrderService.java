package com.team6.ecommerce.order;



import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class OrderService {

    /*  - getAllOrders (tüm orderler gözükür, işlem halinde olanlar en üstte gözükür?)
     *  - cancelOrder (orderId alır, eğer bu kullanıcının boyle bir orderi daha once varsa, işlem durumu "PROCESSING" ise order iptal edilir yani aldığı ürünler adetleri ile
     *      bağlı olduğu product'un quantity değişkenine yazılır, ödediği para ise iade edilir.
     *
     *  - getAllOrdersByUserIdAdmin (admin'in id vererek istedigi kisinin order'ini sıralaması)
     *  - getAllOrdersAdmin (direkt bütün orderlerin listelenmesi), bunu çağıran controller metodu requestparameter alacak ?=PROCESSING , ?=INTRANSIT , ?= DELIVERED
     *  -
     */


    private final OrderRepository orderRepo;
    private final UserService userService;


    @Secured({"ROLE_ADMIN"})
    public List<Order> getAllOrdersAdmin( OrderStatus orderStatus){
        return orderRepo.findAllByOrderStatus( orderStatus );
    }


    @Secured({"ROLE_ADMIN"})
    public List<Order> getAllOrdersByUserIdAdmin(String userId, OrderStatus orderStatus){

        if ( orderStatus == null){
            return orderRepo.findAll();
        } else {
            return orderRepo.findAllByUserIdAndOrderStatus(userId, orderStatus);
        }
    }


    @Scheduled(fixedRate = 60000) // Runs every 1 minute
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



    @Scheduled(fixedRate = 180000) // Runs every 3 minutes
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























