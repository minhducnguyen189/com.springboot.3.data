package com.springboot.project.repository;


import com.springboot.project.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID>,
        JpaSpecificationExecutor<CustomerEntity> {

    CustomerEntity findCustomerByEmail(String email);

}
