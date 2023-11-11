package com.springboot.project.repository;


import com.springboot.project.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findCustomerByEmail(String email);

    @Query(
            value = "       SELECT * FROM customers c                                                                                                                                                                                  " +
                    "       WHERE to_tsvector('english', full_name || ' ' || email || ' ' || address || ' ' || regexp_replace(phone, '-', '', 'g') || ' ' || regexp_replace(CAST(dob AS TEXT), '-', '', 'g') || ' ' || gender)         " +
                    "       @@ plainto_tsquery('english', ?1);                                                                                                                                                                         ",
            nativeQuery = true
    )
    List<CustomerEntity> searchCustomerByKeyword(String keyword);

}
