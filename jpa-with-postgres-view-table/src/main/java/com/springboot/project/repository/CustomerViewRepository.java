package com.springboot.project.repository;

import com.springboot.project.entity.CustomerViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerViewRepository
    extends JpaRepository<CustomerViewEntity, UUID>, JpaSpecificationExecutor<CustomerViewEntity> {}
