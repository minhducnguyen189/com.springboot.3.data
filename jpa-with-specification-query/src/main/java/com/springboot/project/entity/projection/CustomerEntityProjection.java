package com.springboot.project.entity.projection;

import com.springboot.project.entity.Gender;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.entity.OrderEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

 public interface CustomerEntityProjection {

     UUID getId();
     String getFullName();
     String getEmail();
     String getAddress();
     String getPhone();
     Gender getGender();
     Date getDob();
     Integer getLoyaltyCardPoints();
     LoyaltyCardEntity getLoyaltyCard();
     List<OrderEntity> getOrders();
    
}
