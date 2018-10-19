package com.chekh.cafemanager.service.order;

import com.chekh.cafemanager.entity.CafeOrder;
import com.chekh.cafemanager.entity.ProductInOrder;
import com.chekh.cafemanager.misc.OrderStatus;
import com.chekh.cafemanager.service.order.error.InvalidOrderStateException;
import com.chekh.cafemanager.service.order.error.OrderNotFoundException;
import com.chekh.cafemanager.service.error.TableNotFoundException;
import com.chekh.cafemanager.service.order.model.OrderCreateRequest;
import com.chekh.cafemanager.service.order.model.OrderModifyRequest;
import com.chekh.cafemanager.service.product.ProductService;
import com.chekh.cafemanager.service.table.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductInOrderRepository productInOrderRepository;

    @Autowired
    private TableService tableService;

    @Autowired
    private ProductService productService;

    @Transactional
    public OrderCreateRequest createOrder(OrderCreateRequest request) {
        if (!tableService.tableExists(request.getTableId()))
            throw new TableNotFoundException("Cannot find table with id:{" + request.getTableId() + "}");

        if (hasOpenOrder(request.getTableId()))
            throw new InvalidOrderStateException("Table already has an open order");


        CafeOrder cafeOrder = new CafeOrder();
        cafeOrder.setCafeTable(tableService.get(request.getTableId()));
        cafeOrder.setOrderStatus(OrderStatus.OPEN);
        cafeOrder.setOrderProducts(mapDtoToEntity(null, request.getOrderItems()));

//        CafeOrder savedCafeOrder = orderRepository.save(cafeOrder);
//
//        Set<ProductInOrder> productInOrders = mapDtoToEntity(savedCafeOrder, request.getOrderItems());
//        List<ProductInOrder> savedProducts = productInOrderRepository.saveAll(productInOrders);
//
//        return new OrderCreateRequest(savedCafeOrder.getId(), request.getTableId(), mapEntityToDto(savedProducts));

        CafeOrder savedOrder = orderRepository.save(cafeOrder);
        return new OrderCreateRequest(savedOrder.getId(), request.getTableId(), mapEntityToDto(savedOrder.getOrderProducts()));
    }

    @Transactional
    public OrderCreateRequest updateOrder(OrderCreateRequest request) {
        if (!tableService.tableExists(request.getTableId()))
            throw new TableNotFoundException("Cannot find table with id:{" + request.getTableId() + "}");

        if (!hasOpenOrderWithId(request.getTableId(), request.getOrderId())) {
            throw new OrderNotFoundException("Cannot find OPEN order with id: {" + request.getOrderId() + "} on table with id: {" + request.getTableId() + "}");
        }

        Map<String, ProductInOrder> currentMap = new HashMap<>();
        Set<ProductInOrder> diff = mapDtoToEntity(orderRepository.getOne(request.getOrderId()), request.getOrderItems());

        productInOrderRepository.getAllByCafeOrder_Id(request.getOrderId()).forEach(it -> currentMap.put(it.getProduct().getId(), it));

        diff.forEach(it -> {
            if (currentMap.containsKey(it.getProduct().getId())) {
                ProductInOrder productInOrder = currentMap.get(it.getProduct().getId());
                productInOrder.setCount(productInOrder.getCount() + it.getCount());
            } else {
                currentMap.put(it.getProduct().getId(), it);
            }
        });

        return new OrderCreateRequest(request.getOrderId(), request.getTableId(), mapEntityToDto(currentMap.values()));
    }

    @Transactional
    public void changeOrderStatus(OrderModifyRequest request) {
        if (!tableService.tableExists(request.getTableId()))
            throw new TableNotFoundException("Cannot find table with id:{" + request.getTableId() + "}");

        if (!hasOpenOrderWithId(request.getTableId(), request.getOrderId())) {
            throw new OrderNotFoundException("Cannot find OPEN order with id: {" + request.getOrderId() + "} on table with id: {" + request.getTableId() + "}");
        }

        CafeOrder cafeOrder = orderRepository.getOne(request.getOrderId());
        cafeOrder.setOrderStatus(OrderStatus.CLOSED);

        orderRepository.save(cafeOrder);
    }

    private boolean hasOpenOrder(String tableId) {
        return orderRepository.findOrdersByTableIdAndStatus(tableService.get(tableId), OrderStatus.OPEN).size() > 0;
    }

    private boolean hasOpenOrderWithId(String tableId, String orderId) {
        return orderRepository.findOrderByIdAndStatus(tableService.get(tableId), orderId, OrderStatus.OPEN) != null;
    }

    private Set<ProductInOrder> mapDtoToEntity(CafeOrder savedCafeOrder, Set<OrderCreateRequest.OrderItem> orderItems) {
        return orderItems.stream().map(it -> {
            ProductInOrder productInOrder = new ProductInOrder();

//            productInOrder.setCafeOrder(savedCafeOrder);
            productInOrder.setCount(it.getCount());
            productInOrder.setProduct(productService.getProductWith(it.getProductId()));

            return productInOrder;
        }).collect(Collectors.toSet());
    }

    private Set<OrderCreateRequest.OrderItem> mapEntityToDto(Collection<ProductInOrder> orderItems) {
        return orderItems.stream().map(it -> new OrderCreateRequest.OrderItem(it.getProduct().getId(), it.getCount()))
                .collect(Collectors.toSet());
    }
}