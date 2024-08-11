package com.springboot.project.secondary.repository;

import com.springboot.project.secondary.entity.GlobalCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GlobalCustomerRepository extends JpaRepository<GlobalCustomerEntity, UUID> {}
