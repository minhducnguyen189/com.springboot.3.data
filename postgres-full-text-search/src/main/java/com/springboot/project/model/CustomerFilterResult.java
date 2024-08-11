package com.springboot.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerFilterResult {

  List<Customer> filteredCustomers;
  Long foundNumber;
  Long total;
}
