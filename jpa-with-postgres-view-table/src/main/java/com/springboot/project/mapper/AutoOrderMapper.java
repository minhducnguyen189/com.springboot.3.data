package com.springboot.project.mapper;

import com.springboot.project.entity.OrderEntity;
import com.springboot.project.generated.model.OrderRequest;
import com.springboot.project.generated.model.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoOrderMapper {

    AutoOrderMapper MAPPER = Mappers.getMapper(AutoOrderMapper.class);

    OrderEntity mapToEntity(OrderRequest order);

    OrderResponse mapToOrderResponse(OrderEntity order);

}
