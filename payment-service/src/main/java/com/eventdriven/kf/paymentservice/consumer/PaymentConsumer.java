package com.eventdriven.kf.paymentservice.consumer;

import com.eventdriven.kf.paymentservice.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    @KafkaListener(
            topics = "orders-topic",
            groupId = "order-group"
    )
    public void consume(OrderEvent event) {

        System.out.println("🔥 Received Order Event:");
        System.out.println("OrderId: " + event.getOrderId());
        System.out.println("Product: " + event.getProduct());
        System.out.println("Amount: " + event.getAmount());

        // Simulate payment processing
        processPayment(event);
    }

    private void processPayment(OrderEvent event) {
        System.out.println("💰 Processing payment for Order: " + event.getOrderId());
    }
}
