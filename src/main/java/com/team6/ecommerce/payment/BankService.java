package com.team6.ecommerce.payment;

import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.payment.dto.PaymentRequest;
import com.team6.ecommerce.payment.dto.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
/*
@Log4j2
@Service
public class BankService {

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("[BankService] Processing payment for card: {}", paymentRequest.getCardNumber());

        //card validation
        if (!isValidCard(paymentRequest.getCardNumber())) {
            return new PaymentResponse(false, Strings.PAYMENT_FAILED_INVALID_CARD_DETAILS);
        }

        // Simulate payment processing
        boolean sufficientFunds = simulateSufficientFunds();
        if (!sufficientFunds) {
            return new PaymentResponse(false, Strings.PAYMENT_FAILED_INSUFFICIENT_FUNDS);
        }

        // If all checks pass, return success
        return new PaymentResponse(true, Strings.PAYMENT_SUCCESSFUL);
    }

    private boolean isValidCard(String cardNumber) {
        // Mock validation: Card should be 16 digits and not null
        return cardNumber != null && cardNumber.length() == 16;
    }

    private boolean simulateSufficientFunds() {
        // Simulate random outcome (e.g., 80% success, 20% failure)
        return Math.random() > 0.2;
    }
}
*/
