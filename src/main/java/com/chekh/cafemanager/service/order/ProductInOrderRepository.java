package com.chekh.cafemanager.service.order;

import com.chekh.cafemanager.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, String> {
    Set<ProductInOrder> getAllByCafeOrder_Id(String cafeOrderId);
}
