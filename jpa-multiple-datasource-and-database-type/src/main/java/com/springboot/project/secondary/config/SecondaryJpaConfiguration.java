package com.springboot.project.secondary.config;

import java.util.HashMap;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"${application.multiple-datasource.secondary.base-package}"},
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager")
public class SecondaryJpaConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
      @Qualifier("secondaryDataSource") DataSource dataSource,
      SecondaryConfigProperty configProperty,
      EntityManagerFactoryBuilder builder) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
            builder.dataSource(dataSource).packages(configProperty.getBasePackage()).build();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabasePlatform(configProperty.getHibernateDialect());
    vendorAdapter.setGenerateDdl(configProperty.isGenerateDdl());
    vendorAdapter.setShowSql(configProperty.isShowSql());
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    HashMap<String, String> properties = new HashMap<>();
    properties.put("hibernate.dialect", configProperty.getHibernateDialect());
    properties.put("hibernate.show_sql", String.valueOf(configProperty.isShowSql()));
    properties.put("hibernate.use_sql_comments", configProperty.getUseSqlComments());
    properties.put("hibernate.format_sql", configProperty.getFormatSql());
    properties.put("hibernate.hbm2ddl.auto", configProperty.getHibernateHbm2dllAuto());
    entityManagerFactoryBean.setJpaPropertyMap(properties);
    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager secondaryTransactionManager(
      @Qualifier("secondaryEntityManagerFactory")
          LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
    return new JpaTransactionManager(
        Objects.requireNonNull(secondaryEntityManagerFactory.getObject()));
  }
}
