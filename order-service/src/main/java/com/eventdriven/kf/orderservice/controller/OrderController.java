package com.eventdriven.kf.orderservice.controller;

import com.eventdriven.kf.orderservice.dto.OrderEvent;
import com.eventdriven.kf.orderservice.entity.Order;
import com.eventdriven.kf.orderservice.repository.OrderRepository;
import com.eventdriven.kf.orderservice.service.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderProducer producer;

    @PostMapping
    public String createOrder(@RequestBody Order order) {

        // 1️⃣ Save to DB
        Order saved = repository.save(order);

        // 2️⃣ Create event
        OrderEvent event = new OrderEvent();
        event.setOrderId(saved.getId());
        event.setProduct(saved.getProduct());
        event.setAmount(saved.getAmount());

        // 3️⃣ Send to Kafka
        producer.sendOrderEvent(event);

        return "Order created and event sent";
    }

    // Just to test idempotency
    @PostMapping("/resend")
    public String resend(@RequestBody OrderEvent event) {
        producer.sendOrderEvent(event);
        return "Event resent";
    }
}
