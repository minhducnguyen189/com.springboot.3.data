package com.springboot.project.secondary.controller;

import com.springboot.project.generated.secondary.api.GlobalCustomerApi;
import com.springboot.project.generated.secondary.model.GlobalCustomer;
import com.springboot.project.generated.secondary.model.GlobalCustomerRequest;
import com.springboot.project.secondary.service.GlobalCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondaryCustomerController implements GlobalCustomerApi {

  private final GlobalCustomerService globalCustomerService;

  @Autowired
  public SecondaryCustomerController(GlobalCustomerService globalCustomerService) {
    this.globalCustomerService = globalCustomerService;
  }

  @Override
  public ResponseEntity<GlobalCustomer> addGlobalCustomer(
      GlobalCustomerRequest globalCustomerRequest) {
    return new ResponseEntity<>(
        this.globalCustomerService.addCustomer(globalCustomerRequest), HttpStatus.CREATED);
  }
}
