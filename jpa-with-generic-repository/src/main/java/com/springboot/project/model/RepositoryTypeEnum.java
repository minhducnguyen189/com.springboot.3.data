package com.springboot.project.model;

import com.springboot.project.repository.CustomerRepository;
import com.springboot.project.repository.GenericRepository;
import com.springboot.project.repository.ItemRepository;
import com.springboot.project.repository.OrderRepository;

public enum RepositoryTypeEnum {

    ITEM(ItemRepository.class),
    ORDER(OrderRepository.class),
    CUSTOMER(CustomerRepository.class);

    private final Class<? extends GenericRepository<?>> repoClass;

    RepositoryTypeEnum(Class<? extends GenericRepository<?>> repoClass) {
        this.repoClass = repoClass;
    }

    public Class<? extends GenericRepository<?>> getRepoClass() {
        return repoClass;
    }

}
