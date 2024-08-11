package com.springboot.project.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
/** This entity is used for primary DB with the schema = "primary" */
@Table(schema = "public", name = "global_customer")
public class GlobalCustomerEntity {

  @Id @GeneratedValue private UUID id;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "address")
  private String address;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "dob")
  private LocalDate dob;
}
