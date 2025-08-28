package com.springboot.project.repository;

import com.springboot.project.model.ActionTypeEnum;

import java.util.function.Consumer;

public interface GenericRepository<T> {

    Consumer<T> writeAction(ActionTypeEnum action);

}
