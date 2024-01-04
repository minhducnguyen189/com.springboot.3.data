package com.springboot.project.controller;


import com.springboot.project.generated.api.OrderApi;
import com.springboot.project.generated.model.OrderRequest;
import com.springboot.project.generated.model.OrderResponse;
import com.springboot.project.mapper.AutoOrderMapper;
import com.springboot.project.model.Order;
import com.springboot.project.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> addOrder(UUID customerId, OrderRequest orderRequest) {
        Order order = AutoOrderMapper.MAPPER.mapToOrderFromRequest(orderRequest);
        order = this.orderService.createOrder(customerId, order);
        OrderResponse orderResponse = AutoOrderMapper.MAPPER.mapToOrderResponse(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderDetail(UUID customerId, UUID orderId) {
        Order order = this.orderService.getOrderDetail(customerId, orderId);
        OrderResponse orderResponse = AutoOrderMapper.MAPPER.mapToOrderResponse(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

}
