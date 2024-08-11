package com.springboot.project.service;

import com.springboot.project.generated.model.Customer;
import com.springboot.project.generated.model.CustomerRequest;

public interface PrimaryCustomerService {

  Customer addCustomer(CustomerRequest customerRequest);
}
