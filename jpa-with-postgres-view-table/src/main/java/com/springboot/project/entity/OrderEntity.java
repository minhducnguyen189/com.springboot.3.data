package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String orderName;

  private LocalDateTime createdDate;

  private LocalDateTime lastUpdatedDate;

  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ItemEntity> items = new ArrayList<>();

  @PrePersist
  private void setCreatedDate() {
    LocalDateTime localDateTime = LocalDateTime.now();
    this.createdDate = localDateTime;
    this.lastUpdatedDate = localDateTime;
  }

  @PreUpdate
  private void setLastUpdatedDate() {
    this.lastUpdatedDate = LocalDateTime.now();
  }
}
