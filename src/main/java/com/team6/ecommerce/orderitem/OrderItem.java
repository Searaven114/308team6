package com.team6.ecommerce.orderitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Setter
@Getter
@Document(collection = "orderitem")
public class OrderItem {

    private String id;
    private int quantity;
    private Double price;


}

/*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;*/