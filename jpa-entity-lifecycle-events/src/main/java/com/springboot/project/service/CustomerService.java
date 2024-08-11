package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

  private final CustomerRepository customerRepository;

  public Customer createCustomer(Customer customer) {
    CustomerEntity customerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
    customerEntity = this.customerRepository.save(customerEntity);
    return AutoCustomerMapper.MAPPER.mapToCustomer(customerEntity);
  }

  public Customer getCustomer(UUID customerId) {
    Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
    if (customerEntity.isPresent()) {
      return AutoCustomerMapper.MAPPER.mapToCustomer(customerEntity.get());
    }
    throw new RuntimeException("Customer Not Found!");
  }

  public List<Customer> filterCustomers(CustomerFilter customerFilter) {
    List<CustomerEntity> foundCustomers =
        this.customerRepository.filterCustomers(
            customerFilter.getFullName(),
            customerFilter.getEmail(),
            customerFilter.getAddress(),
            customerFilter.getPhone());
    return AutoCustomerMapper.MAPPER.mapToCustomers(foundCustomers);
  }

  public Customer updateCustomer(UUID customerId, Customer customer) {
    Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
    if (customerEntity.isPresent()) {
      CustomerEntity existedCustomerEntity = customerEntity.get();
      CustomerEntity updateCustomerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
      AutoCustomerMapper.MAPPER.updateCustomerEntity(existedCustomerEntity, updateCustomerEntity);
      existedCustomerEntity = this.customerRepository.save(existedCustomerEntity);
      return AutoCustomerMapper.MAPPER.mapToCustomer(existedCustomerEntity);
    }
    throw new RuntimeException("Customer Not Found!");
  }

  public void deleteCustomer(UUID customerId) {
    this.customerRepository.deleteById(customerId);
  }

  public Customer findCustomerByEmail(String email) {
    Optional<CustomerEntity> customerEntity = this.customerRepository.findCustomerByEmail(email);
    if (customerEntity.isPresent()) {
      return AutoCustomerMapper.MAPPER.mapToCustomer(customerEntity.get());
    }
    throw new RuntimeException("Customer Not Found! with email: " + email);
  }
}
