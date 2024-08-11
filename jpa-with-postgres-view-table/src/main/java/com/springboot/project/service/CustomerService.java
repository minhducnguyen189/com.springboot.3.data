package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.CustomerViewEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.generated.model.*;
import com.springboot.project.helper.SpecificationHelper;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.CustomerViewRepository;
import com.springboot.project.share.QueryFields;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final CustomerViewRepository customerViewRepository;

  public CustomerResponse createCustomer(CustomerRequest customer) {
    CustomerEntity customerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
    customerEntity = this.customerRepository.save(customerEntity);
    return AutoCustomerMapper.MAPPER.mapToCustomerResponse(customerEntity);
  }

  public LoyaltyCardResponse createLoyaltyCard(UUID customerId, LoyaltyCardRequest loyaltyCard) {
    CustomerEntity customerEntity = this.getCustomerEntity(customerId);
    LoyaltyCardEntity loyaltyCardEntity =
        AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardEntity(loyaltyCard);
    customerEntity.setLoyaltyCard(loyaltyCardEntity);
    customerEntity = this.customerRepository.save(customerEntity);
    return AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardResponse(customerEntity.getLoyaltyCard());
  }

  public CustomerResponse getCustomer(UUID customerId) {
    return AutoCustomerMapper.MAPPER.mapToCustomerResponse(this.getCustomerEntity(customerId));
  }

  public CustomerFilterResponse filterCustomerView(CustomerFilter customerFilter) {

    Pageable pageable =
        PageRequest.of(customerFilter.getPageNumber(), customerFilter.getPageSize());
    if (Objects.nonNull(customerFilter.getSortBy())
        && Objects.nonNull(customerFilter.getSortOrder())) {
      pageable =
          PageRequest.of(
              customerFilter.getPageNumber(),
              customerFilter.getPageSize(),
              Sort.by(
                  Sort.Direction.valueOf(customerFilter.getSortOrder()),
                  customerFilter.getSortBy()));
    }

    CustomerViewEntity exampleCustomer = new CustomerViewEntity();
    exampleCustomer.setFullName(customerFilter.getFullName());
    exampleCustomer.setEmail(customerFilter.getEmail());
    exampleCustomer.setAddress(customerFilter.getAddress());
    exampleCustomer.setPhone(customerFilter.getPhone());
    exampleCustomer.setGender(customerFilter.getGender());

    ExampleMatcher exampleMatcher =
        ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withIgnoreCase()
            .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    Example<CustomerViewEntity> customerEntityExample = Example.of(exampleCustomer, exampleMatcher);

    Specification<CustomerViewEntity> specification =
        Specification.where(SpecificationHelper.initSpecificationWithExample(customerEntityExample))
            .and(
                SpecificationHelper.queryDateBetweenSpecification(
                    QueryFields.DOB, customerFilter.getDobFrom(), customerFilter.getDobTo()))
            .and(
                SpecificationHelper.queryJoinTableNumberEqualSpecification(
                    QueryFields.LOYALTY_CARD,
                    QueryFields.POINTS,
                    customerFilter.getLoyaltyCardPoints()));

    Page<CustomerViewEntity> customerEntityPage =
        this.customerViewRepository.findAll(specification, pageable);
    CustomerFilterResponse customerFilterResult = new CustomerFilterResponse();
    customerFilterResult.setCustomers(
        AutoCustomerMapper.MAPPER.mapToCustomersView(customerEntityPage.getContent()));
    customerFilterResult.setFoundNumber(this.customerViewRepository.count(specification));
    customerFilterResult.setTotalNumber(this.customerViewRepository.count());

    return customerFilterResult;
  }

  public void updateCustomer(UUID customerId, CustomerRequest customer) {
    Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
    if (customerEntity.isPresent()) {
      CustomerEntity existedCustomerEntity = customerEntity.get();
      CustomerEntity updateCustomerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
      AutoCustomerMapper.MAPPER.updateCustomerEntity(existedCustomerEntity, updateCustomerEntity);
      this.customerRepository.save(existedCustomerEntity);
      return;
    }
    throw new RuntimeException("Customer Not Found!");
  }

  public void deleteCustomer(UUID customerId) {
    this.customerRepository.deleteById(customerId);
  }

  private CustomerEntity getCustomerEntity(UUID customerId) {
    Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
    if (customerEntity.isPresent()) {
      return customerEntity.get();
    }
    throw new RuntimeException("Customer Not Found!");
  }
}
