package com.team6.ecommerce.payment;

import com.team6.ecommerce.payment.dto.PaymentRequest;
import com.team6.ecommerce.payment.dto.PaymentResponse;
import com.team6.ecommerce.constants.Strings;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Mock validation logic
        if (isValidCard(paymentRequest.getCardNumber())) {
            return new PaymentResponse(true, "Payment successful");
        }
        return new PaymentResponse(false, Strings.PAYMENT_FAILED_INVALID_CARD_DETAILS);
    }

    private boolean isValidCard(String cardNumber) {
        // Simple mock validation (e.g., 16-digit check)
        return cardNumber != null && cardNumber.length() == 16;
    }


}
