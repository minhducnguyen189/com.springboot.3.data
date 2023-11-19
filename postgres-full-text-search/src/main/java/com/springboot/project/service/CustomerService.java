package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilterResult;
import com.springboot.project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public CustomerFilterResult searchCustomer(String keyword, Integer pageSize, Integer pageNumber) {
        int defaultPageSize = 10;
        int defaultPageNumber = 0;
        if (Objects.isNull(pageSize) || Objects.isNull(pageNumber) || pageSize < 10 || pageNumber < 0) {
            pageSize = defaultPageSize;
            pageNumber = defaultPageNumber;
        }
        List<CustomerEntity> foundCustomers = this.customerRepository.searchCustomerByKeyword(keyword, pageSize, pageNumber);
        List<Customer> customers = AutoCustomerMapper.MAPPER.mapToCustomers(foundCustomers);
        CustomerFilterResult customerFilterResult = new CustomerFilterResult();
        customerFilterResult.setFilteredCustomers(customers);
        customerFilterResult.setFoundNumber((long) customers.size());
        customerFilterResult.setTotal(this.customerRepository.count());
        return customerFilterResult;
    }

    public void updateCustomer(UUID customerId, Customer customer) {
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
            CustomerEntity existedCustomerEntity = customerEntity.get();
            CustomerEntity updateCustomerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
            AutoCustomerMapper.MAPPER.updateCustomerEntity(existedCustomerEntity, updateCustomerEntity);
            this.customerRepository.save(existedCustomerEntity);
            return;
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
