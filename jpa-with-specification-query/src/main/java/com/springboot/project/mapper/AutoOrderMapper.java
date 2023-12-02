package com.springboot.project.mapper;

import com.springboot.project.entity.OrderEntity;
import com.springboot.project.generated.model.OrderRequest;
import com.springboot.project.generated.model.OrderResponse;
import com.springboot.project.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoOrderMapper {

    AutoOrderMapper MAPPER = Mappers.getMapper(AutoOrderMapper.class);

    Order mapToOrderFromRequest(OrderRequest orderRequest);

    OrderEntity mapToEntity(Order order);

    OrderResponse mapToOrderResponse(Order order);

    Order mapToOrderFromEntity(OrderEntity orderEntity);

}
