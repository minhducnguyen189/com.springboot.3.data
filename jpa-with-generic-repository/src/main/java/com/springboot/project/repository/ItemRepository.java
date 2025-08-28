package com.springboot.project.repository;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.ItemEntity;
import com.springboot.project.model.ActionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID>, GenericRepository<ItemEntity> {

}
