package com.springboot.project.primary.mapper;

import com.springboot.project.generated.primary.model.Customer;
import com.springboot.project.generated.primary.model.CustomerRequest;
import com.springboot.project.primary.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerEntity toCustomerEntity(CustomerRequest customerRequest);

    Customer toCustomer(CustomerEntity customerEntity);

}
