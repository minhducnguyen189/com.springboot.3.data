package com.springboot.project.primary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
/** This entity is used for primary DB with the schema = "primary" */
@Table(schema = "public", name = "customer")
public class CustomerEntity {

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
