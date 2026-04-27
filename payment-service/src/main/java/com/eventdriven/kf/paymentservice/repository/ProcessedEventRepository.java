package com.eventdriven.kf.paymentservice.repository;

import com.eventdriven.kf.paymentservice.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {
}
