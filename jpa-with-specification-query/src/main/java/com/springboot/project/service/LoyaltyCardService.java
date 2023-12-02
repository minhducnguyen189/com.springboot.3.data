package com.springboot.project.service;


import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import com.springboot.project.mapper.AutoLoyaltyCardMapper;
import com.springboot.project.model.LoyaltyCard;
import com.springboot.project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoyaltyCardService {

    private final CustomerRepository customerRepository;

    public LoyaltyCard createLoyaltyCard(UUID customerId, LoyaltyCard loyaltyCard) {
        CustomerEntity customerEntity = this.getCustomer(customerId);
        LoyaltyCardEntity loyaltyCardEntity = AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCardEntity(loyaltyCard);
        customerEntity.setLoyaltyCard(loyaltyCardEntity);
        customerEntity = this.customerRepository.save(customerEntity);
        return AutoLoyaltyCardMapper.MAPPER.mapToLoyaltyCard(customerEntity.getLoyaltyCard());
    }

    private CustomerEntity getCustomer(UUID customerId) {
        Optional<CustomerEntity> customerEntity = this.customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
            return customerEntity.get();
        }
        throw new RuntimeException("Customer Not Found!");
    }

}
