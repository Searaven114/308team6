package com.team6.ecommerce.order;



import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    //private static CartService cartService;

//    private void refund ( String userId, String OrderId){
//
//    }


//    public OrderResponse createOrder(HttpServletRequest request){
//        long total = 0;
//        User user = getUserByToken(request, jwtService, this.userRepository);
//        Cart cart = this.cartRepository.findByUserId(user.getId());
//        Calendar calendar = Calendar.getInstance();
//
//        if(cart.getCartItems() == null)
//            throw new RuntimeException("Can not create order with empty cart");
//
//        for(CartItem cartItem : cart.getCartItems()){
//            total = (long) cartItem.getQuantity() * cartItem.getProduct().getPrice();
//        }
//
//        Order savedOrder = this.orderRepository.save(
//                Order.builder()
//                        .orderStatus(OrderStatus.PENDING)
//                        .cart(cart)
//                        .createdAt(calendar.getTime())
//                        .orderStatus(OrderStatus.PENDING)
//                        .processedAt(null)
//                        .total(total)
//                        .build()
//        );
//
//
//        CartResponse cartResponse = CartResponse.builder()
//                .id(savedOrder.getCart().getId())
//                .user(
//                        LoggedUserResponse.builder()
//                                .id(savedOrder.getCart().getUser().getId())
//                                .firstname(savedOrder.getCart().getUser().getFirstName())
//                                .lastname(savedOrder.getCart().getUser().getLastName())
//                                .email(savedOrder.getCart().getUser().getEmail())
//                                .mobileNumber(savedOrder.getCart().getUser().getMobileNumber())
//                                .build()
//                )
//                .cartItems(savedOrder.getCart().getCartItems())
//                .build();
//
//        //clear cart once order is creates
//        this.cartRepository.delete(cart);
//        return OrderResponse.builder()
//                .id(savedOrder.getId())
//                .cart(cartResponse)
//                .orderStatus(savedOrder.getOrderStatus())
//                .createdAt(savedOrder.getCreatedAt())
//                .total(savedOrder.getTotal())
//                .processedAt(savedOrder.getProcessedAt())
//                .build();
//    }


    public String createOrder(String user_id){


        //Cart boş mu degil mi checki, boş ise order yapamaz.

        return "" ;
    }



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

