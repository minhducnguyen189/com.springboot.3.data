package com.springboot.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LoyaltyCard {

    private UUID id;
    private Integer points;
    private Date issueDate;

}
