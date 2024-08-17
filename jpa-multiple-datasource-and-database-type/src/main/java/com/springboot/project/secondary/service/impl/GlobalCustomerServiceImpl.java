package com.springboot.project.secondary.service.impl;

import com.springboot.project.generated.secondary.model.GlobalCustomer;
import com.springboot.project.generated.secondary.model.GlobalCustomerRequest;
import com.springboot.project.secondary.entity.GlobalCustomerEntity;
import com.springboot.project.secondary.mapper.GlobalCustomerMapper;
import com.springboot.project.secondary.repository.GlobalCustomerRepository;
import com.springboot.project.secondary.service.GlobalCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalCustomerServiceImpl implements GlobalCustomerService {

  private final GlobalCustomerRepository globalCustomerRepository;

  @Autowired
  public GlobalCustomerServiceImpl(GlobalCustomerRepository globalCustomerRepository) {
    this.globalCustomerRepository = globalCustomerRepository;
  }

  @Override
  public GlobalCustomer addCustomer(GlobalCustomerRequest globalCustomerRequest) {
    GlobalCustomerEntity globalCustomerEntity =
        GlobalCustomerMapper.MAPPER.toGlobalCustomerEntity(globalCustomerRequest);
    return GlobalCustomerMapper.MAPPER.toGlobalCustomer(
        this.globalCustomerRepository.save(globalCustomerEntity));
  }
}
