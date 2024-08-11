package com.springboot.project.primary.controller;

import com.springboot.project.generated.primary.api.CustomerApi;
import com.springboot.project.generated.primary.model.Customer;
import com.springboot.project.generated.primary.model.CustomerRequest;
import com.springboot.project.primary.service.PrimaryCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimaryCustomerController implements CustomerApi {

  private final PrimaryCustomerService primaryCustomerService;

  @Autowired
  public PrimaryCustomerController(PrimaryCustomerService primaryCustomerService) {
    this.primaryCustomerService = primaryCustomerService;
  }

  @Override
  public ResponseEntity<Customer> addCustomer(CustomerRequest customerRequest) {
    return new ResponseEntity<>(
        this.primaryCustomerService.addCustomer(customerRequest), HttpStatus.CREATED);
  }
}
