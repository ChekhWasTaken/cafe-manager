package com.chekh.cafemanager.service.table.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableGetMyResponse {
    private List<Table> myTables;
}
