package com.springboot.project.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {},
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager")
public class SecondaryJpaConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
      @Qualifier("secondaryDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource).packages("").build();
  }

  @Bean
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("secondaryEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
    return new JpaTransactionManager(
        Objects.requireNonNull(secondaryEntityManagerFactory.getObject()));
  }
}
