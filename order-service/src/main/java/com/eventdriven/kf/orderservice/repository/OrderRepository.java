package com.eventdriven.kf.orderservice.repository;

import com.eventdriven.kf.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
