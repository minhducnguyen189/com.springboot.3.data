package com.springboot.project.primary.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PrimaryDataSourceConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSourceProperties primaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  public DataSource primaryDataSource() {
    return primaryDataSourceProperties().initializeDataSourceBuilder().build();
  }
}
