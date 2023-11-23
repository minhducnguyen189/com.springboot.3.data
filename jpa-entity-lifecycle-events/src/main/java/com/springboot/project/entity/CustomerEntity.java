package com.springboot.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
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
        log.info("Attempting to add new Customer with username: " + this.fullName);
    }

    @PostPersist
    public void logNewCustomerAdded() {
        log.info("Added Customer '" + this.fullName + "' with ID: " + this.id);
    }

    @PreRemove
    public void logCustomerRemovalAttempt() {
        log.info("Attempting to delete Customer: " + this.fullName);
    }

    @PostRemove
    public void logCustomerRemoval() {
        log.info("Deleted Customer: " + this.fullName);
    }

    @PreUpdate
    public void logCustomerUpdateAttempt() {
        log.info("Attempting to update Customer: " + this.fullName);
    }

    @PostUpdate
    public void logCustomerUpdate() {
        log.info("Updated Customer: " + this.fullName);
    }

    @PostLoad
    public void logCustomerLoad() {
        log.info(this.fullName);
    }

}
