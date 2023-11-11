package com.springboot.project.mapper;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import com.springboot.project.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AutoCustomerMapper {

    AutoCustomerMapper MAPPER = Mappers.getMapper(AutoCustomerMapper.class);

    Customer mapToCustomerFromRequest(CustomerRequest customerRequest);

    Customer mapToCustomer(CustomerEntity customerEntity);

    CustomerEntity mapToCustomerEntity(Customer customer);

    CustomerResponse mapToCustomerResponse(Customer customer);

    void updateCustomerEntity(@MappingTarget CustomerEntity customerEntityTarget, CustomerEntity updateEntity);

    List<CustomerEntity> mapToCustomerEntities(List<Customer> customers);

    List<Customer> mapToCustomers(List<CustomerEntity> customerEntities);

    List<CustomerResponse> mapToCustomerResponses(List<Customer> customers);



}
