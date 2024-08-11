package com.springboot.project.controller;

import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.Customer;
import com.springboot.project.generated.model.CustomerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController implements CustomerApi {

  @Override
  public ResponseEntity<Customer> addCustomer(Optional<CustomerRequest> customerRequest) {
    return null;
  }
}
