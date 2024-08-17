package com.springboot.project.primary.service;


import com.springboot.project.generated.primary.model.Customer;
import com.springboot.project.generated.primary.model.CustomerRequest;

public interface PrimaryCustomerService {

  Customer addCustomer(CustomerRequest customerRequest);
}
