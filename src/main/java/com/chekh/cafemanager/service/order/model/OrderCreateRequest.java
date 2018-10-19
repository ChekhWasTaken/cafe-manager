package com.chekh.cafemanager.service.order.model;

import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
public class OrderCreateRequest {
    private String orderId;

    private String tableId;

    private Set<OrderItem> orderItems;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class OrderItem {
        private String productId;

        @EqualsAndHashCode.Exclude
        private int count;
    }
}
