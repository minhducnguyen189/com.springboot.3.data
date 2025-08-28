package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.generated.model.*;
import com.springboot.project.helper.RepositoryFactory;
import com.springboot.project.helper.SpecificationHelper;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.ActionTypeEnum;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.model.RepositoryTypeEnum;
import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.GenericRepository;
import com.springboot.project.share.QueryFields;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

  private final RepositoryFactory repositoryFactory;

  public CustomerResponse createCustomer(CustomerRequest customer) {
    CustomerEntity customerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
    customerEntity =
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .save(customerEntity);
    return AutoCustomerMapper.MAPPER.mapToCustomerResponse(customerEntity);
  }

  public LoyaltyCardResponse createLoyaltyCard(UUID customerId, LoyaltyCardRequest loyaltyCard) {
    CustomerEntity customerEntity = this.getCustomerEntity(customerId);
    LoyaltyCardEntity loyaltyCardEntity =
        AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardEntity(loyaltyCard);
    customerEntity.setLoyaltyCard(loyaltyCardEntity);
    customerEntity =
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .save(customerEntity);
    return AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardResponse(customerEntity.getLoyaltyCard());
  }

  public CustomerResponse getCustomer(UUID customerId) {
    return AutoCustomerMapper.MAPPER.mapToCustomerResponse(this.getCustomerEntity(customerId));
  }

  public CustomerFilterResponse filterCustomerWithEM(CustomerFilter customerFilter) {

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

    CustomerEntity exampleCustomer = new CustomerEntity();
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

    Example<CustomerEntity> customerEntityExample = Example.of(exampleCustomer, exampleMatcher);

    Specification<CustomerEntity> specification =
        Specification.where(SpecificationHelper.initSpecificationWithExample(customerEntityExample))
            .and(
                SpecificationHelper.queryDateBetweenSpecification(
                    QueryFields.DOB, customerFilter.getDobFrom(), customerFilter.getDobTo()))
            .and(
                SpecificationHelper.queryJoinTableNumberEqualSpecification(
                    QueryFields.LOYALTY_CARD,
                    QueryFields.POINTS,
                    customerFilter.getLoyaltyCardPoints()));

    Page<CustomerEntity> customerEntityPage =
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .findAll(specification, pageable);
    CustomerFilterResponse customerFilterResult = new CustomerFilterResponse();
    customerFilterResult.setCustomers(
        AutoCustomerMapper.MAPPER.mapToCustomers(customerEntityPage.getContent()));
    customerFilterResult.setFoundNumber(
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .count(specification));
    customerFilterResult.setTotalNumber(
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .count());

    return customerFilterResult;
  }

  public void updateCustomer(UUID customerId, CustomerRequest customer) {
    Optional<CustomerEntity> customerEntity =
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .findById(customerId);
    if (customerEntity.isPresent()) {
      CustomerEntity existedCustomerEntity = customerEntity.get();
      CustomerEntity updateCustomerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
      AutoCustomerMapper.MAPPER.updateCustomerEntity(existedCustomerEntity, updateCustomerEntity);
      this.repositoryFactory
          .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
          .save(existedCustomerEntity);
      return;
    }
    throw new RuntimeException("Customer Not Found!");
  }

  public void deleteCustomer(UUID customerId) {
    this.repositoryFactory
        .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
        .deleteById(customerId);
  }

  private CustomerEntity getCustomerEntity(UUID customerId) {
    Optional<CustomerEntity> customerEntity =
        this.repositoryFactory
            .getRepository(RepositoryTypeEnum.CUSTOMER, CustomerRepository.class)
            .findById(customerId);
    if (customerEntity.isPresent()) {
      return customerEntity.get();
    }
    throw new RuntimeException("Customer Not Found!");
  }
}
