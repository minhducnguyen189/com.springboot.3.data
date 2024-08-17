package com.springboot.project.secondary.service;


import com.springboot.project.generated.secondary.model.GlobalCustomer;
import com.springboot.project.generated.secondary.model.GlobalCustomerRequest;

public interface GlobalCustomerService {

  GlobalCustomer addCustomer(GlobalCustomerRequest globalCustomerRequest);
}
