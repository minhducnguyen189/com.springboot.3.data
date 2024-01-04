package com.springboot.project.controller;


import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import com.springboot.project.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> addCustomer(CustomerRequest customerRequest) {
        CustomerResponse customerResponse = this.customerService.createCustomer(customerRequest);;
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerInfo(UUID customerId) {
        CustomerResponse customerResponse = this.customerService.getCustomer(customerId);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

}
