package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customers_view")
@Immutable
public class CustomerViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String address;
    @Column(unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dob;
    private Integer points;

}
