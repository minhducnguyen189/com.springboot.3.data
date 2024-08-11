package com.springboot.project.mapper;

import com.springboot.project.entity.ItemEntity;
import com.springboot.project.generated.model.ItemRequest;
import com.springboot.project.generated.model.ItemResponse;
import com.springboot.project.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoItemMapper {

  AutoItemMapper MAPPER = Mappers.getMapper(AutoItemMapper.class);

  Item toItemFromRequest(ItemRequest itemRequest);

  Item toItemFromEntity(ItemEntity itemEntity);

  ItemEntity toItemEntity(Item item);

  ItemResponse toItemResponse(Item item);
}
