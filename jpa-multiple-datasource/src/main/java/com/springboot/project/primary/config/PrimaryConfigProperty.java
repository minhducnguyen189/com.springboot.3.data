package com.springboot.project.primary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.multiple-datasource.primary")
public class PrimaryConfigProperty {

  private String basePackage;
}
