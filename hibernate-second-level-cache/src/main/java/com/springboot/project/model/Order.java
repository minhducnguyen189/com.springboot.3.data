package com.springboot.project.model;

import com.springboot.project.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private UUID id;
    private UUID customerId;
    private String orderName;
    private OrderStatus orderStatus;
    private List<Item> items;

}
