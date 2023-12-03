package com.springboot.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerFilterResult {

    private List<CustomerFilterDetail> customers;
    private Long foundNumber;
    private Long totalNumber;

}
