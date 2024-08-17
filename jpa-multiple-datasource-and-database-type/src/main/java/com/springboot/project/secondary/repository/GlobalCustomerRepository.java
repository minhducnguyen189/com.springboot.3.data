package com.springboot.project.secondary.repository;

import com.springboot.project.secondary.entity.GlobalCustomerEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCustomerRepository extends JpaRepository<GlobalCustomerEntity, UUID> {}
