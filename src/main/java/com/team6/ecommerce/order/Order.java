package com.team6.ecommerce.order;

import com.team6.ecommerce.cart.Cart;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private Cart cart;
    private OrderStatus orderStatus;
    private Date createdAt;
    private Long total;

}







