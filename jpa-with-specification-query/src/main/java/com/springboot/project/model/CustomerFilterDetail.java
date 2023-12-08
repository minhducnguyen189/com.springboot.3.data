package com.springboot.project.model;

import com.springboot.project.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomerFilterDetail {

    private UUID id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private Date dob;
    private LoyaltyCard loyaltyCard;
    private List<Order> orders;

}
