package com.springboot.project.mapper;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.CustomerViewEntity;
import com.springboot.project.generated.model.CustomerFilterDetailResponse;
import com.springboot.project.generated.model.CustomerRequest;
import com.springboot.project.generated.model.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AutoCustomerMapper {

    AutoCustomerMapper MAPPER = Mappers.getMapper(AutoCustomerMapper.class);

    CustomerEntity mapToCustomerEntity(CustomerRequest customer);

    CustomerResponse mapToCustomerResponse(CustomerEntity customer);

    void updateCustomerEntity(@MappingTarget CustomerEntity customerEntityTarget, CustomerEntity updateEntity);

    List<CustomerFilterDetailResponse> mapToCustomers(List<CustomerEntity> customerEntities);

    List<CustomerFilterDetailResponse> mapToCustomersView(List<CustomerViewEntity> customerEntities);



}
