package com.springboot.project.model;

import com.springboot.project.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

  private UUID id;
  private String fullName;
  private String email;
  private String address;
  private String phone;
  private Gender gender;
  private Date dob;
}
