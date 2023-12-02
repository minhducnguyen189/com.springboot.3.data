package com.springboot.project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customers", indexes = {
        @Index(name = "uniqueEmailIndex", columnList = "email", unique = true),
        @Index(name = "uniquePhoneIndex", columnList = "phone", unique = true),
        @Index(name = "uniqueMultiIndex", columnList = "email, phone", unique = true)
})
public class CustomerEntity {

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

    @JoinColumn(name = "loyalty_card")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LoyaltyCardEntity loyaltyCard;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderEntity> orders = new ArrayList<>();

}
