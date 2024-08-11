package com.springboot.project.mapper;

import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.generated.model.LoyaltyCardRequest;
import com.springboot.project.generated.model.LoyaltyCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoLoyaltyCardMapper {

  AutoLoyaltyCardMapper MAPPER = Mappers.getMapper(AutoLoyaltyCardMapper.class);

  LoyaltyCardEntity mapToLoyaltyCardEntity(LoyaltyCardRequest loyaltyCard);

  LoyaltyCardResponse mapToLoyaltyCardResponse(LoyaltyCardEntity loyaltyCard);
}
