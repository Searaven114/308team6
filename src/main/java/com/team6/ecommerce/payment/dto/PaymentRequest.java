package com.team6.ecommerce.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String cardNumber;
    private String cardExpiry;
    private String cvv;
    private Long totalAmount;
}
