package com.springboot.project.service;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.helper.SpecificationHelper;
import com.springboot.project.mapper.AutoCustomerMapper;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.Customer;
import com.springboot.project.model.CustomerFilter;
import com.springboot.project.model.CustomerFilterResult;
import com.springboot.project.model.LoyaltyCard;
import com.springboot.project.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final EntityManager entityManager;

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

    public CustomerFilterResult filterCustomerWithEM(CustomerFilter customerFilter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> query = builder.createQuery(CustomerEntity.class);
        Root<CustomerEntity> rootTable = query.from(CustomerEntity.class);

        if (Objects.nonNull(customerFilter.getSortBy()) && Objects.nonNull(customerFilter.getSortOrder())) {
            if(customerFilter.getSortBy().equals("DESC")) {
                query.orderBy(builder.desc(rootTable.get(customerFilter.getSortBy())));
            } else {
                query.orderBy(builder.asc(rootTable.get(customerFilter.getSortBy())));
            }
        }

        CustomerEntity exampleCustomer = new CustomerEntity();
        exampleCustomer.setFullName(customerFilter.getFullName());
        exampleCustomer.setEmail(customerFilter.getEmail());
        exampleCustomer.setAddress(customerFilter.getAddress());
        exampleCustomer.setPhone(customerFilter.getPhone());
        exampleCustomer.setGender(customerFilter.getGender());

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

        Predicate predicate = specification.toPredicate(rootTable, query, builder);

        CriteriaQuery<CustomerEntity> customerEntityProjectionCriteriaQuery =  query.where(predicate);
        TypedQuery<CustomerEntity> jpaQuery = entityManager.createQuery(customerEntityProjectionCriteriaQuery);
        jpaQuery.setFirstResult(customerFilter.getPageNumber());
        jpaQuery.setMaxResults(customerFilter.getPageSize());

        List<CustomerEntity> results = jpaQuery.getResultList();
        long total = this.customerRepository.count();

        CustomerFilterResult customerFilterResult = new CustomerFilterResult();
        customerFilterResult.setCustomers(AutoCustomerMapper.MAPPER.mapToCustomers(results));
        customerFilterResult.setFoundNumber(this.customerRepository.count(specification));
        customerFilterResult.setTotalNumber(total);

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
