package com.springboot.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFilter {

  private String fullName;
  private String address;
  private String email;
  private String phone;
}
