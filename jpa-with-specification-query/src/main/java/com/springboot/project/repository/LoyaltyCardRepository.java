package com.springboot.project.repository;

import com.springboot.project.entity.LoyaltyCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCardEntity, UUID> {}
