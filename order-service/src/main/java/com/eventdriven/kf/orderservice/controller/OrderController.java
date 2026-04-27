package com.eventdriven.kf.orderservice.controller;

import com.eventdriven.kf.orderservice.dto.OrderEvent;
import com.eventdriven.kf.orderservice.entity.Order;
import com.eventdriven.kf.orderservice.entity.OutboxEvent;
import com.eventdriven.kf.orderservice.repository.OrderRepository;
import com.eventdriven.kf.orderservice.repository.OutboxRepository;
import com.eventdriven.kf.orderservice.service.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OutboxRepository outboxRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final OrderProducer producer;

    @Transactional
    @PostMapping
    public String createOrder(@RequestBody Order order) throws JsonProcessingException {

        Order saved = orderRepository.save(order);

        OrderEvent event = new OrderEvent();
        event.setOrderId(saved.getId());
        event.setProduct(saved.getProduct());
        event.setAmount(saved.getAmount());

        String payload = objectMapper.writeValueAsString(event);

        OutboxEvent outbox = new OutboxEvent();
        outbox.setEventType("ORDER_CREATED");
        outbox.setPayload(payload);
        outbox.setStatus("NEW");

        outboxRepository.save(outbox);

        log.info("Order saved: {}", saved.getId());
        log.info("Outbox event created");

        return "Order created and event stored in outbox";
    }

    @PostMapping("/resend")
    public String resend(@RequestBody OrderEvent event) {
        producer.sendOrderEvent(event);
        return "Event resent";
    }
}
