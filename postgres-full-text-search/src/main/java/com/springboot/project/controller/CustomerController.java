package com.springboot.project.controller;

import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> addCustomer(CustomerRequest customerRequest) {
        Customer customer = AutoCustomerMapper.MAPPER.mapToCustomerFromRequest(customerRequest);
        customer = this.customerService.createCustomer(customer);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerInfo(UUID customerId) {
        Customer customer = this.customerService.getCustomer(customerId);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerInfoByEmail(String email) {
        Customer customer = this.customerService.findCustomerByEmail(email);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> searchCustomers(String keyword) {
        List<Customer> customers = this.customerService.searchCustomer(keyword);
        List<CustomerResponse> customerResponses = AutoCustomerMapper.MAPPER.mapToCustomerResponses(customers);
        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }

    }
