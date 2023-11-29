package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EntityListeners(CustomerListener.class)
@Entity
@Slf4j
@Table(name = "customers", indexes = {
        @Index(name = "uniqueEmailIndex", columnList = "email", unique = true),
        @Index(name = "uniquePhoneIndex", columnList = "phone", unique = true),
        @Index(name = "uniqueMultiIndex", columnList = "email, phone", unique = true)
})
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dob;

    @PrePersist
    public void logNewCustomerAttempt() {
        log.info("@PrePersist Attempting to add new Customer with username: " + this.fullName);
    }

    @PostPersist
    public void logNewCustomerAdded() {
        log.info("@PostPersist Added Customer '" + this.fullName + "' with ID: " + this.id);
    }

    @PreRemove
    public void logCustomerRemovalAttempt() {
        log.info("@PreRemove Attempting to delete Customer: " + this.fullName);
    }

    @PostRemove
    public void logCustomerRemoval() {
        log.info("@PostRemove Deleted Customer: " + this.fullName);
    }

    @PreUpdate
    public void logCustomerUpdateAttempt() {
        log.info("@PreUpdate Attempting to update Customer: " + this.fullName);
    }

    @PostUpdate
    public void logCustomerUpdate() {
        log.info("@PostUpdate Updated Customer: " + this.fullName);
    }

    @PostLoad
    public void logCustomerLoad() {
        log.info("@PostLoad load customer: " + this.fullName);
    }

}
