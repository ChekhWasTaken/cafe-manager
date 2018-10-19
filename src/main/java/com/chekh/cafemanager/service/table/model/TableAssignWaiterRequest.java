package com.chekh.cafemanager.service.table.model;

import lombok.Data;

@Data
public class TableAssignWaiterRequest {
    private String tableId;

    private String waiterId;
}
