package com.springboot.project.repository;

import com.springboot.project.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

  Optional<CustomerEntity> findCustomerByEmail(String email);

  @Query(
      value =
          "  SELECT * FROM customers c                                                      "
              + /*1*/ "  WHERE (:full_name is null or c.full_name = :full_name)                         "
              + /*2*/ "  AND   (:email is null or c.email = :email)                                     "
              + /*3*/ "  AND   (:address is null or c.address = :address)                               "
              + /*4*/ "  AND   (:phone is null or c.phone = :phone);                                    ", /*5*/
      nativeQuery = true)
  List<CustomerEntity> filterCustomers(
      @Param("full_name") String fullName,
      @Param("email") String email,
      @Param("address") String address,
      @Param("phone") String phone);
}
