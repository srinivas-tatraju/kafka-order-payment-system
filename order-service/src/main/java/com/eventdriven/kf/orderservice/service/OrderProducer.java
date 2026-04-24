package com.eventdriven.kf.orderservice.service;

import com.eventdriven.kf.orderservice.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "orders-topic";

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event.getOrderId().toString(), event);
        System.out.println("✅ Sent event to Kafka: " + event.getOrderId());
    }
}
