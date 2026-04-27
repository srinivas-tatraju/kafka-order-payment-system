package com.eventdriven.kf.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEvent {

    @Id
    private Long orderId;

    private LocalDateTime processedAt = LocalDateTime.now();

    public ProcessedEvent(Long orderId) {
        this.orderId = orderId;
        this.processedAt = LocalDateTime.now();
    }
}
