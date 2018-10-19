package com.chekh.cafemanager.service.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderModifyRequest {
    private String tableId;
    private String orderId;
}
