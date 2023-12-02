package com.springboot.project.controller;

import com.springboot.project.generated.api.LoyaltyCardApi;
import com.springboot.project.generated.model.LoyaltyCardRequest;
import com.springboot.project.generated.model.LoyaltyCardResponse;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.LoyaltyCard;
import com.springboot.project.service.LoyaltyCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoyaltyCardController implements LoyaltyCardApi {

    private final LoyaltyCardService loyaltyCardService;

    @Override
    public ResponseEntity<LoyaltyCardResponse> addloyaltyCard(UUID customerId, LoyaltyCardRequest loyaltyCardRequest) {
        LoyaltyCard loyaltyCard = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardFromRequest(loyaltyCardRequest);
        loyaltyCard = this.loyaltyCardService.createLoyaltyCard(customerId, loyaltyCard);
        LoyaltyCardResponse loyaltyCardResponse = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardResponse(loyaltyCard);
        return new ResponseEntity<>(loyaltyCardResponse, HttpStatus.OK);
    }

}