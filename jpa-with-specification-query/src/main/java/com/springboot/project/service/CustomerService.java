package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.entity.projection.CustomerEntityProjection;
import com.springboot.project.helper.SpecificationHelper;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.model.CustomerFilterResult;
import com.springboot.project.model.LoyaltyCard;
import com.springboot.project.repository.CustomerRepository;
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

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity = AutoCustomerMapper.MAPPER.mapToCustomerEntity(customer);
        customerEntity = this.customerRepository.save(customerEntity);
        return AutoCustomerMapper.MAPPER.mapToCustomer(customerEntity);
    }

    public LoyaltyCard createLoyaltyCard(UUID customerId, LoyaltyCard loyaltyCard) {
        CustomerEntity customerEntity = this.getCustomerEntity(customerId);
        LoyaltyCardEntity loyaltyCardEntity = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardEntity(loyaltyCard);
        customerEntity.setLoyaltyCard(loyaltyCardEntity);
        customerEntity = this.customerRepository.save(customerEntity);
        return AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCard(customerEntity.getLoyaltyCard());
    }

    public Customer getCustomer(UUID customerId) {
        return AutoCustomerMapper.MAPPER.mapToCustomer(this.getCustomerEntity(customerId));
    }

    public CustomerFilterResult filterCustomer(CustomerFilter customerFilter) {
        CustomerEntity exampleCustomer = new CustomerEntity();
        exampleCustomer.setFullName(customerFilter.getFullName());
        exampleCustomer.setEmail(customerFilter.getEmail());
        exampleCustomer.setAddress(customerFilter.getAddress());
        exampleCustomer.setPhone(customerFilter.getPhone());
        exampleCustomer.setGender(customerFilter.getGender());

        Pageable pageable;
        if (Objects.nonNull(customerFilter.getSortBy()) && Objects.nonNull(customerFilter.getSortOrder())) {
            pageable = PageRequest.of(
                    customerFilter.getPageNumber(),
                    customerFilter.getPageSize(),
                    Sort.by(Sort.Direction.valueOf(customerFilter.getSortOrder()), customerFilter.getSortBy())
            );
        } else {
            pageable = PageRequest.of(customerFilter.getPageNumber(), customerFilter.getPageSize());
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<CustomerEntity> customerEntityExample = Example.of(exampleCustomer, exampleMatcher);

        Specification<CustomerEntity> specification = Specification
                .where(SpecificationHelper.initSpecificationWithExample(customerEntityExample))
                .and(SpecificationHelper.queryDateBetweenSpecification(
                        "dob",
                        customerFilter.getDobFrom(),
                        customerFilter.getDobTo()
                ))
                .and(SpecificationHelper.queryJoinTableNumberEqualSpecification(
                        "loyaltyCard",
                        "points",
                        customerFilter.getLoyaltyCardPoints()
                ));

        Page<CustomerEntityProjection> page = this.customerRepository
                .findBy(specification, queryFunction -> queryFunction.as(CustomerEntityProjection.class).page(pageable));

        Long foundNumber = this.customerRepository.count(specification);
        Long totalNumber = this.customerRepository.count();

        CustomerFilterResult customerFilterResult = new CustomerFilterResult();
        customerFilterResult.setCustomers(AutoCustomerMapper.MAPPER.mapToCustomers(page.getContent()));
        customerFilterResult.setFoundNumber(foundNumber);
        customerFilterResult.setTotalNumber(totalNumber);

        return customerFilterResult;
    }

    public void updateCustomer(UUID customerId, Customer customer) {
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
