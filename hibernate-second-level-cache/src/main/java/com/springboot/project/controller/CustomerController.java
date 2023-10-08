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
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> addCustomer(CustomerRequest customerRequest) {
        Customer customer = AutoCustomerMapper.MAPPER.mapToCustomerFromRequest(customerRequest);
        customer = this.customerService.createCustomer(customer);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

}
