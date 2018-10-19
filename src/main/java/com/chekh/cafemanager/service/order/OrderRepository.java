package com.chekh.cafemanager.service.order;

import com.chekh.cafemanager.entity.CafeOrder;
import com.chekh.cafemanager.entity.CafeTable;
import com.chekh.cafemanager.misc.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CafeOrder, String> {
    @Query("SELECT co FROM CafeOrder co JOIN co.cafeTable  WHERE co.cafeTable=(:cafeTable) AND co.orderStatus=(:orderStatus)")
    List<CafeOrder> findOrdersByTableIdAndStatus(CafeTable cafeTable, OrderStatus orderStatus);

    @Query("SELECT co FROM CafeOrder co JOIN co.cafeTable WHERE co.cafeTable=(:cafeTable) AND co.orderStatus=(:orderStatus) AND co.id=(:orderId)")
    CafeOrder findOrderByIdAndStatus(CafeTable cafeTable, String orderId, OrderStatus orderStatus);
}


/*
SELECT a FROM Author a JOIN a.books b WHERE b.title LIKE '%Hibernate%'
 */