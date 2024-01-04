package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customer) {
        CustomerEntity customerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
        customerEntity = this.customerRepository.save(customerEntity);
        return AutoCustomerMapper.MAPPER.mapToCustomerResponse(customerEntity);
    }

    public CustomerResponse getCustomer(UUID customerId) {
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
            return AutoCustomerMapper.MAPPER.mapToCustomerResponse(customerEntity.get());
        }
        throw new RuntimeException("Customer Not Found!");
    }

    public void updateCustomer(UUID customerId, CustomerRequest customer) {
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

}
