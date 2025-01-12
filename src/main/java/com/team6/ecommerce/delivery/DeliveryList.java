package com.team6.ecommerce.delivery;

import com.team6.ecommerce.address.Address;
import com.team6.ecommerce.cartitem.CartItem2;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "deliveries")
public class DeliveryList {

    @Id
    private String id;

    private String customerId;

//    private String productId;
//
//    private int quantity;
//
//    private double totalPrice;

    private CartItem2 cartItem2;

    private Address deliveryAddress;

    private boolean isCompleted;

    private Date createdDate;
}
