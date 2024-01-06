package com.springboot.project.service;

import com.springboot.project.entity.ItemEntity;
import com.springboot.project.entity.OrderEntity;
import com.springboot.project.generated.model.ItemRequest;
import com.springboot.project.generated.model.ItemResponse;
import com.springboot.project.mapper.AutoItemMapper;
import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.ItemRepository;
import com.springboot.project.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    private List<ItemResponse> addItemToOrder(UUID orderId, List<ItemRequest> items) {
        OrderEntity orderEntity = this.getOrderEntity(orderId);
        List<ItemEntity> itemEntities = this.toItemEntities(items);
        itemEntities.forEach(i -> i.setOrder(orderEntity));
        List<ItemEntity> itemEntityResults = this.itemRepository.saveAll(itemEntities);
        return this.toItems(itemEntityResults);
    }

    private List<ItemEntity> toItemEntities(List<ItemRequest> items) {
        return items.stream().map(AutoItemMapper.MAPPER::toItemEntity).collect(Collectors.toList());
    }

    private List<ItemResponse> toItems(List<ItemEntity> itemEntities) {
        return itemEntities.stream().map(AutoItemMapper.MAPPER::toItemResponse).collect(Collectors.toList());
    }

    private OrderEntity getOrderEntity(UUID orderId) {
        Optional<OrderEntity> orderEntity = this.orderRepository.findById(orderId);
        if (orderEntity.isPresent()) {
            return orderEntity.get();
        }
        throw new RuntimeException("Order Not Found!");
    }

}
