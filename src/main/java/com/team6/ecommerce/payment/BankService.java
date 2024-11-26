package com.team6.ecommerce.payment;

import com.team6.ecommerce.payment.dto.PaymentRequest;
import com.team6.ecommerce.payment.dto.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BankService {

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("[BankService] Processing payment for card: {}", paymentRequest.getCardNumber());

        // Mock card validation
        if (!isValidCard(paymentRequest.getCardNumber())) {
            return new PaymentResponse(false, "Payment failed: Invalid card details");
        }

        // Simulate payment processing
        boolean sufficientFunds = simulateSufficientFunds();
        if (!sufficientFunds) {
            return new PaymentResponse(false, "Payment failed: Insufficient funds");
        }

        // If all checks pass, return success
        return new PaymentResponse(true, "Payment successful");
    }

    private boolean isValidCard(String cardNumber) {
        // Mock validation: Card should be 16 digits and not null
        return cardNumber != null && cardNumber.length() == 16;
    }

    private boolean simulateSufficientFunds() {
        // Simulate random outcome (e.g., 70% success, 30% failure)
        return Math.random() > 0.3;
    }
}

