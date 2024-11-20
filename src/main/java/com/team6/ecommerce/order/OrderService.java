package com.team6.ecommerce.order;



import com.team6.ecommerce.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

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

    //private static CartService cartService;

//    private void refund ( String userId, String OrderId){
//
//    }


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























}

