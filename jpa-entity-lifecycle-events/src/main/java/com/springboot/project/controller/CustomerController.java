package com.springboot.project.controller;

import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.CustomerDetail;
import com.springboot.project.generated.model.CustomerFilterRequest;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilter;
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
    public ResponseEntity<CustomerDetail> addCustomer(CustomerRequest customerRequest) {
        Customer customer = AutoCustomerMapper.MAPPER.mapToCustomerFromRequest(customerRequest);
        customer = this.customerService.createCustomer(customer);
        CustomerDetail customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerDetail(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerDetail> getCustomer(UUID customerId) {
        Customer customer = this.customerService.getCustomer(customerId);
        CustomerDetail customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerDetail(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CustomerDetail> getCustomerInfoByEmail(String email) {
        Customer customer = this.customerService.findCustomerByEmail(email);
        CustomerDetail customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerDetail(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CustomerDetail>> filterCustomers(CustomerFilterRequest customerFilterRequest) {
        CustomerFilter customerFilter = AutoCustomerMapper.MAPPER.mapToCustomerFilter(customerFilterRequest);
        List<Customer> customers = this.customerService.filterCustomers(customerFilter);
        List<CustomerDetail> customerResponses = AutoCustomerMapper.MAPPER.mapToCustomerDetails(customers);
        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(UUID customerId) {
        this.customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDetail> updateCustomer(UUID customerId, CustomerDetail customerDetail) {
        Customer customer = AutoCustomerMapper.MAPPER.mapToCustomerFromDetail(customerDetail);
        customer = this.customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(AutoCustomerMapper.MAPPER.mapToCustomerDetail(customer), HttpStatus.OK);
    }

}
