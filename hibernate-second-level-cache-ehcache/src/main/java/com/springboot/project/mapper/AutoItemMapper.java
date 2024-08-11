package com.springboot.project.mapper;

import com.springboot.project.entity.ItemEntity;
import com.springboot.project.generated.model.ItemRequest;
import com.springboot.project.generated.model.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoItemMapper {

  AutoItemMapper MAPPER = Mappers.getMapper(AutoItemMapper.class);

  ItemResponse toItemFromEntity(ItemEntity itemEntity);

  ItemEntity toItemEntity(ItemRequest item);
}
