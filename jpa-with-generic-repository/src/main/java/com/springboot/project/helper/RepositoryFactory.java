package com.springboot.project.helper;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.ItemEntity;
import com.springboot.project.model.RepositoryTypeEnum;
import com.springboot.project.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {

  private final GenericRepository<ItemEntity> itemRepository;
  private final GenericRepository<CustomerEntity> customerRepository;

  @Autowired
  public RepositoryFactory(
      GenericRepository<ItemEntity> itemRepository,
      GenericRepository<CustomerEntity> customerRepository) {
    this.itemRepository = itemRepository;
    this.customerRepository = customerRepository;
  }

  public GenericRepository<?> getRepository(RepositoryTypeEnum type) {
    return switch (type) {
      case ITEM -> this.itemRepository;
      case CUSTOMER -> this.customerRepository;
    };
  }
}
