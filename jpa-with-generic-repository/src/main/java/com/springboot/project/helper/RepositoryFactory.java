package com.springboot.project.helper;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.ItemEntity;
import com.springboot.project.model.RepositoryTypeEnum;
import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.GenericRepository;
import com.springboot.project.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {

  private final ItemRepository itemRepository;
  private final CustomerRepository customerRepository;

  @Autowired
  public RepositoryFactory(ItemRepository itemRepository, CustomerRepository customerRepository) {
    this.itemRepository = itemRepository;
    this.customerRepository = customerRepository;
  }

  public <T extends GenericRepository<?>> T getRepository(
      RepositoryTypeEnum type, Class<T> expectedType) {
    GenericRepository<?> repo =
        switch (type) {
          case ITEM -> itemRepository;
          case CUSTOMER -> customerRepository;
        };
    return expectedType.cast(repo);
  }
}
