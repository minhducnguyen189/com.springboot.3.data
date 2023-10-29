package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "items", indexes = {
        @Index(name = "uniqueItemNameIndex", columnList = "itemName", unique = true),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String itemName;

    private Long quantity;

    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

}
