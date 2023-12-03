package com.springboot.project.controller;


import com.springboot.project.entity.Gender;
import com.springboot.project.generated.api.CustomerApi;
import com.springboot.project.generated.model.CustomerFilterResponse;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import com.springboot.project.generated.model.LoyaltyCardRequest;
import com.springboot.project.generated.model.LoyaltyCardResponse;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.model.CustomerFilterResult;
import com.springboot.project.model.LoyaltyCard;
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
        Customer customer = AutoCustomerMapper.MAPPER.mapToCustomerFromRequest(customerRequest);
        customer = this.customerService.createCustomer(customer);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoyaltyCardResponse> addloyaltyCard(UUID customerId, LoyaltyCardRequest loyaltyCardRequest) {
        LoyaltyCard loyaltyCard = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardFromRequest(loyaltyCardRequest);
        loyaltyCard = this.customerService.createLoyaltyCard(customerId, loyaltyCard);
        LoyaltyCardResponse loyaltyCardResponse = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardResponse(loyaltyCard);
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
        CustomerFilterResult customerFilterResult = this.customerService.filterCustomer(customerFilter);
        CustomerFilterResponse customerFilterResponse = AutoCustomerMapper.MAPPER.mapToCustomerFilterResponse(customerFilterResult);
        return new ResponseEntity<>(customerFilterResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerInfo(UUID customerId) {
        Customer customer = this.customerService.getCustomer(customerId);
        CustomerResponse customerResponse = AutoCustomerMapper.MAPPER.mapToCustomerResponse(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

}
