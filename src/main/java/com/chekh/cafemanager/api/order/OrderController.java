package com.chekh.cafemanager.api.order;

import com.chekh.cafemanager.api.InvalidParameterException;
import com.chekh.cafemanager.service.order.OrderService;
import com.chekh.cafemanager.service.order.model.OrderCreateRequest;
import com.chekh.cafemanager.service.order.model.OrderModifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/table/{tableId}/order", method = RequestMethod.POST)
    public ResponseEntity<OrderCreateRequest> createOrderForTable(@PathVariable String tableId, @RequestBody OrderCreateRequest request) {
        request.setTableId(tableId);

        validateCreateRequest(request);

        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/table/{tableId}/order", method = RequestMethod.PUT)
    public ResponseEntity<OrderCreateRequest> updateOrderForTable(@PathVariable String tableId, @RequestBody OrderCreateRequest request) {
        request.setTableId(tableId);

        validateUpdateRequest(request);

        return new ResponseEntity<>(orderService.updateOrder(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/table/{tableId}/order", method = RequestMethod.PATCH)
    public ResponseEntity<Void> changeOrderStatusForTable(@PathVariable String tableId, @RequestBody OrderModifyRequest request) {
        request.setTableId(tableId);

        validateChangeStatusRequest(request);

        orderService.changeOrderStatus(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void validateChangeStatusRequest(OrderModifyRequest request) {
        if (StringUtils.isEmpty(request.getTableId())) {
            throw new InvalidParameterException("Wrong table id");
        }

        if (StringUtils.isEmpty(request.getOrderId())) {
            throw new InvalidParameterException("Order id should not be empty");
        }
    }

    private void validateCreateRequest(OrderCreateRequest request) {
        if (StringUtils.isEmpty(request.getTableId())) throw new InvalidParameterException("Wrong table id");

        if (request.getOrderItems() == null || request.getOrderItems().isEmpty())
            throw new InvalidParameterException("Order items should not be empty");
    }

    private void validateUpdateRequest(OrderCreateRequest request) {
        validateCreateRequest(request);

        if (StringUtils.isEmpty(request.getOrderId()))
            throw new InvalidParameterException("Order id should not be empty");
    }
}
