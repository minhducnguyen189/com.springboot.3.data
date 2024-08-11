package com.springboot.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Item {

  private UUID id;
  private UUID orderId;
  private String itemName;
  private Long quantity;
  private Float price;
}
