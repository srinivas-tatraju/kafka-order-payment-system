package com.eventdriven.kf.orderservice.producer;

import com.eventdriven.kf.orderservice.dto.OrderEvent;
import com.eventdriven.kf.orderservice.entity.OutboxEvent;
import com.eventdriven.kf.orderservice.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxRepository repository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishEvents() {

        List<OutboxEvent> events = repository.findByStatus("NEW");

        for (OutboxEvent event : events) {
            try {
                // mark processing
                event.setStatus("PROCESSING");
                repository.save(event);

                OrderEvent orderEvent = objectMapper.readValue(event.getPayload(), OrderEvent.class);

                kafkaTemplate.send("orders-topic",
                        orderEvent.getOrderId().toString(),
                        orderEvent).get(); // wait for send

                event.setStatus("SENT");
                repository.save(event);

                log.info("Event published for OrderId: {}", orderEvent.getOrderId());

            } catch (Exception e) {
                log.error("Failed to publish event", e);

                event.setStatus("FAILED");
                repository.save(event);
            }
        }
    }
}
