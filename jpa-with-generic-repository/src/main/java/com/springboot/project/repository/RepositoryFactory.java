package com.springboot.project.repository;

import com.springboot.project.model.RepositoryTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {

  private final ItemRepository itemRepository;
  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;

  @Autowired
  public RepositoryFactory(
      ItemRepository itemRepository,
      CustomerRepository customerRepository,
      OrderRepository orderRepository) {
    this.itemRepository = itemRepository;
    this.customerRepository = customerRepository;
    this.orderRepository = orderRepository;
  }

  public <T extends GenericRepository<?>> T getRepository(
      RepositoryTypeEnum type, Class<T> expectedType) {
    GenericRepository<?> repo =
        switch (type) {
          case ITEM -> this.itemRepository;
          case ORDER -> this.orderRepository;
          case CUSTOMER -> this.customerRepository;
        };
    return expectedType.cast(repo);
  }
}
