package com.springboot.project.secondary.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondaryDataSourceConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.secondary")
  public DataSourceProperties secondaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource secondaryDataSource() {
    return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
  }
}
