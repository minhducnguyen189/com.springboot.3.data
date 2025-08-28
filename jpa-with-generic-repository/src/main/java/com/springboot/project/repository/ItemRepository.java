package com.springboot.project.repository;

import com.springboot.project.entity.ItemEntity;
import com.springboot.project.model.ActionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.function.Consumer;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID>, GenericRepository<ItemEntity> {

    @Override
    default Consumer<ItemEntity> writeAction(ActionTypeEnum action) {
        return switch (action) {
            case CREATE, UPDATE -> this::save;
            case DELETE -> this::delete;
        };
    }

}
