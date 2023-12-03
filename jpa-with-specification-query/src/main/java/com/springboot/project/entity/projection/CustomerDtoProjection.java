package com.springboot.project.entity.projection;

import com.springboot.project.entity.Gender;
import com.springboot.project.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomerDtoProjection {
    private UUID id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private Date dob;
    private Integer loyaltyCardPoints;
    private List<OrderEntity> orders;
}
