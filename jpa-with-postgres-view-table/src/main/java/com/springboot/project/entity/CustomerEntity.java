package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customers")
@NamedEntityGraph(
    name = "CustomerEntity.loyaltyCard",
    attributeNodes = @NamedAttributeNode("loyaltyCard"))
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
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private LoyaltyCardEntity loyaltyCard;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OrderEntity> orders;
}
