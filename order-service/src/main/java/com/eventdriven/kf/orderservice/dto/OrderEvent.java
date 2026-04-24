package com.eventdriven.kf.orderservice.dto;

import lombok.Data;

@Data
public class OrderEvent {

    private Long orderId;
    private String product;
    private Double amount;


}
