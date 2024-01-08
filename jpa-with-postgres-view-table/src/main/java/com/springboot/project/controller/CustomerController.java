package com.springboot.project.controller;


import com.springboot.project.entity.Gender;
import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.*;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> addCustomer(CustomerRequest customerRequest) {
        CustomerResponse customerResponse = this.customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoyaltyCardResponse> addloyaltyCard(UUID customerId, LoyaltyCardRequest loyaltyCardRequest) {
        LoyaltyCardResponse loyaltyCardResponse = this.customerService.createLoyaltyCard(customerId, loyaltyCardRequest);
        return new ResponseEntity<>(loyaltyCardResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerFilterResponse> filterCustomers(
             Optional<String> fullName,
             Optional<String> email,
             Optional<String> address,
             Optional<String> phone,
             Optional<String> gender,
             Optional<LocalDate> dobFrom,
             Optional<LocalDate> dobTo,
             Optional<Integer> loyaltyCardPoints,
             Optional<Integer> pageSize,
             Optional<Integer> pageNumber,
             Optional<String> sortBy,
             Optional<String> sortOrder
    ) {
        CustomerFilter customerFilter = new CustomerFilter();
        customerFilter.setFullName(fullName.orElse(null));
        customerFilter.setEmail(email.orElse(null));
        customerFilter.setAddress(address.orElse(null));
        customerFilter.setPhone(phone.orElse(null));
        customerFilter.setGender(gender.map(Gender::valueOf).orElse(null));
        customerFilter.setDobFrom(dobFrom.map(Date::valueOf).orElse(null));
        customerFilter.setDobTo(dobTo.map(Date::valueOf).orElse(null));
        customerFilter.setLoyaltyCardPoints(loyaltyCardPoints.orElse(null));
        customerFilter.setPageSize(pageSize.orElse(10));
        customerFilter.setPageNumber(pageNumber.orElse(0));
        customerFilter.setSortBy(sortBy.orElse(null));
        customerFilter.setSortOrder(sortOrder.orElse(null));
        CustomerFilterResponse customerFilterResponse = this.customerService.filterCustomerView(customerFilter);
        return new ResponseEntity<>(customerFilterResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerInfo(UUID customerId) {
        CustomerResponse customerResponse = this.customerService.getCustomer(customerId);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

}
