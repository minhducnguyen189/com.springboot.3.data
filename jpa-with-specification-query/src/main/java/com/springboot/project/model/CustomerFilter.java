package com.springboot.project.model;

import com.springboot.project.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerFilter {

  private String fullName;
  private String email;
  private String address;
  private String phone;
  private Gender gender;
  private Date dobFrom;
  private Date dobTo;
  private Integer loyaltyCardPoints;
  private Integer pageSize;
  private Integer pageNumber;
  private String sortBy;
  private String sortOrder;
}
