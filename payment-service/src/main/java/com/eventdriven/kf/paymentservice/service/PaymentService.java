package com.eventdriven.kf.paymentservice.service;

import com.eventdriven.kf.paymentservice.dto.OrderEvent;
import com.eventdriven.kf.paymentservice.entity.ProcessedEvent;
import com.eventdriven.kf.paymentservice.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProcessedEventRepository repository;

    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 2000))
    @KafkaListener(topics = "orders-topic", groupId = "payment-group")
    public void processOrder(OrderEvent event) {

        Long orderId = event.getOrderId();

        // IDEMPOTENCY CHECK
        if (repository.existsById(orderId)) {
            log.warn("Duplicate event ignored for OrderId: {}", orderId);
            return;
        }

        log.info("Processing payment for OrderId: {}", orderId);

        // simulate failure
        if (event.getAmount() > 5000) {
            throw new RuntimeException("Payment failed!");
        }

        // MARK AS PROCESSED
        repository.save(new ProcessedEvent(orderId));

        log.info("Payment successful for OrderId: {}", orderId);
    }

    @DltHandler
    public void handleDlt(OrderEvent event) {
        log.warn("==>DLT RECEIVED - Order permanently failed");
        log.info("OrderId: " + event.getOrderId());
        log.info("Product: " + event.getProduct());
        log.info("Amount: " + event.getAmount());

        // future: save to DB / alert / notify
    }
}
