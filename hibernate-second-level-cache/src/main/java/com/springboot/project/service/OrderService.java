package com.springboot.project.service;


import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.ItemEntity;
import com.springboot.project.entity.OrderEntity;
import com.springboot.project.mapper.AutoItemMapper;
import com.springboot.project.mapper.AutoOrderMapper;
import com.springboot.project.model.Item;
import com.springboot.project.model.Order;
import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(UUID customerId, Order order) {
        if (CollectionUtils.isEmpty(order.getItems())) {
            throw new RuntimeException("Can not create Order without any Item!");
        }
        this.checkCustomerExist(customerId);
        OrderEntity orderEntity = AutoOrderMapper.MAPPER.mapToEntity(order);
        List<ItemEntity> itemEntities = this.mapToItemEntities(order.getItems(), orderEntity);
        orderEntity.setItems(itemEntities);
        orderEntity = this.orderRepository.save(orderEntity);
        return AutoOrderMapper.MAPPER.mapToOrderFromEntity(orderEntity);
    }

    private void checkCustomerExist(UUID customerId) {
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
            return;
        }
        throw new RuntimeException("Customer Not Found!");
    }

    private List<ItemEntity> mapToItemEntities(List<Item> items, OrderEntity orderEntity) {
        return items.stream()
                .map(AutoItemMapper.MAPPER::toItemEntity)
                .peek(i -> i.setOrder(orderEntity))
                .collect(Collectors.toList());
    }

}


