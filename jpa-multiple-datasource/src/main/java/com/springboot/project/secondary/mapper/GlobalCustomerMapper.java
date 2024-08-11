package com.springboot.project.secondary.mapper;

import com.springboot.project.generated.secondary.model.GlobalCustomer;
import com.springboot.project.generated.secondary.model.GlobalCustomerRequest;
import com.springboot.project.secondary.entity.GlobalCustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalCustomerMapper {

    GlobalCustomerMapper MAPPER = Mappers.getMapper(GlobalCustomerMapper.class);

    GlobalCustomerEntity toGlobalCustomerEntity(GlobalCustomerRequest globalCustomerRequest);

    GlobalCustomer toGlobalCustomer(GlobalCustomerEntity globalCustomerEntity);

}
