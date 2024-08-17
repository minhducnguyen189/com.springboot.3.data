package com.springboot.project.primary.config;

import java.util.HashMap;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"${application.multiple-datasource.primary.base-package}"},
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager")
public class PrimaryJpaConfiguration {

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
      @Qualifier("primaryDataSource") DataSource dataSource,
      PrimaryConfigProperty configProperty,
      EntityManagerFactoryBuilder builder) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
            builder.dataSource(dataSource).packages(configProperty.getBasePackage()).build();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, String> properties = new HashMap<>();
    properties.put("hibernate.dialect", configProperty.getHibernateDialect());
    entityManagerFactoryBean.setJpaPropertyMap(properties);
    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("primaryEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
    return new JpaTransactionManager(
        Objects.requireNonNull(primaryEntityManagerFactory.getObject()));
  }
}
