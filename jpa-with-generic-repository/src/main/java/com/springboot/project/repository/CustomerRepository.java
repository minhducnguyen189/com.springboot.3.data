package com.springboot.project.repository;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.model.ActionTypeEnum;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public interface CustomerRepository
    extends JpaRepository<CustomerEntity, UUID>, GenericRepository<CustomerEntity>, JpaSpecificationExecutor<CustomerEntity> {

  CustomerEntity findCustomerByEmail(String email);

  /**
   * If we use @EntityGraph with attributePaths Ex: @EntityGraph(attributePaths = {"loyaltyCard"})
   * we don't need to declare the @NamedEntityGraph in the CustomerEntity. In the example above the
   * `loyaltyCard` is the field name of the entity relationship
   */
  @EntityGraph(value = "CustomerEntity.loyaltyCard")
  @Nonnull
  Page<CustomerEntity> findAll(
      @Nonnull Specification<CustomerEntity> spec, @Nonnull Pageable pageable);

}
