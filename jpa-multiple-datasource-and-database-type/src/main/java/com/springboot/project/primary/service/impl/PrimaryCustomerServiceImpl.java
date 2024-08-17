package com.springboot.project.primary.service.impl;

import com.springboot.project.generated.primary.model.Customer;
import com.springboot.project.generated.primary.model.CustomerRequest;
import com.springboot.project.primary.entity.CustomerEntity;
import com.springboot.project.primary.mapper.CustomerMapper;
import com.springboot.project.primary.repository.CustomerRepository;
import com.springboot.project.primary.service.PrimaryCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimaryCustomerServiceImpl implements PrimaryCustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public PrimaryCustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer addCustomer(CustomerRequest customerRequest) {
    CustomerEntity customerEntity = CustomerMapper.MAPPER.toCustomerEntity(customerRequest);
    return CustomerMapper.MAPPER.toCustomer(this.customerRepository.save(customerEntity));
  }
}
