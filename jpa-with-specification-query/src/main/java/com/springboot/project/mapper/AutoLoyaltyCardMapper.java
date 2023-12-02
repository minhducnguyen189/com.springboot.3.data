package com.springboot.project.mapper;

import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.generated.model.LoyaltyCardRequest;
import com.springboot.project.generated.model.LoyaltyCardResponse;
import com.springboot.project.model.LoyaltyCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoLoyaltyCardMapper {

    AutoLoyaltyCardMapper MAPPER = Mappers.getMapper(AutoLoyaltyCardMapper.class);

    LoyaltyCard mapToLoyaltyCardFromRequest(LoyaltyCardRequest loyaltyCardRequest);

    LoyaltyCard mapToLoyaltyCard(LoyaltyCardEntity loyaltyCardEntity);

    LoyaltyCardEntity mapToLoyaltyCardEntity(LoyaltyCard loyaltyCard);

    LoyaltyCardResponse mapToLoyaltyCardResponse(LoyaltyCard loyaltyCard);


}
