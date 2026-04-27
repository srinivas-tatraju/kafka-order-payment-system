package com.eventdriven.kf.paymentservice.consumer;

import com.eventdriven.kf.paymentservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentConsumer {

    @KafkaListener(
            topics = "orders-topic",
            groupId = "order-group"
    )
    public void consume(OrderEvent event) {

        log.info("Received Order Event:");
        log.info("OrderId: " + event.getOrderId());
        log.info("Product: " + event.getProduct());
        log.info("Amount: " + event.getAmount());

        // Simulate payment processing
        processPayment(event);
    }

    private void processPayment(OrderEvent event) {
        log.info("Processing payment for Order: " + event.getOrderId());
    }
}
