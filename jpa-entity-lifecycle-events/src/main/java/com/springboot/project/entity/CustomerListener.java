package com.springboot.project.entity;


import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerListener {

    @PrePersist
    public void logNewCustomerAttempt(CustomerEntity customerEntity) {
        log.info("CustomerListener Attempting to add new Customer with username: " + customerEntity.getFullName());
    }

    @PostPersist
    public void logNewCustomerAdded(CustomerEntity customerEntity) {
        log.info("CustomerListener Added Customer '" + customerEntity.getFullName() + "' with ID: " + customerEntity.getId());
    }

    @PreRemove
    public void logCustomerRemovalAttempt(CustomerEntity customerEntity) {
        log.info("CustomerListener Attempting to delete Customer: " + customerEntity.getFullName());
    }

    @PostRemove
    public void logCustomerRemoval(CustomerEntity customerEntity) {
        log.info("CustomerListener Deleted Customer: " + customerEntity.getFullName());
    }

    @PreUpdate
    public void logCustomerUpdateAttempt(CustomerEntity customerEntity) {
        log.info("CustomerListener Attempting to update Customer: " + customerEntity.getFullName());
    }

    @PostUpdate
    public void logCustomerUpdate(CustomerEntity customerEntity) {
        log.info("CustomerListener Updated Customer: " + customerEntity.getFullName());
    }

    @PostLoad
    public void logCustomerLoad(CustomerEntity customerEntity) {
        log.info("CustomerListener load customer: " + customerEntity.getFullName());
    }

}
