package com.eventdriven.kf.paymentservice.dto;

import lombok.Data;

@Data
public class OrderEvent {

    private Long orderId;
    private String product;
    private Double amount;


}