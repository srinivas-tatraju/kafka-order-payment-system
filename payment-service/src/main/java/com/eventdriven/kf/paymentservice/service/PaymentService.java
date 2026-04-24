package com.eventdriven.kf.paymentservice.service;

import com.eventdriven.kf.paymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @RetryableTopic(
            attempts = "3",                    // total tries (1 + 2 retries)
            backoff = @Backoff(delay = 2000)   // 2 sec delay
    )
    @KafkaListener(topics = "orders-topic", groupId = "payment-group")
    public void processOrder(OrderEvent event) {
        System.out.println("Processing order: " + event);

        // simulate failure
        if (event.getAmount() > 5000) {
            throw new RuntimeException("Payment failed due to limit!");
        }
        System.out.println("Payment successful for order: " + event.getOrderId());
    }

    @DltHandler
    public void handleDlt(OrderEvent event) {
        System.out.println("💀 DLT RECEIVED - Order permanently failed");
        System.out.println("OrderId: " + event.getOrderId());
        System.out.println("Product: " + event.getProduct());
        System.out.println("Amount: " + event.getAmount());

        // future: save to DB / alert / notify
    }
}
