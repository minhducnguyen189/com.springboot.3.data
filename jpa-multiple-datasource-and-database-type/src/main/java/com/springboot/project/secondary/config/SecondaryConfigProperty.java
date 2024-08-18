package com.springboot.project.secondary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.multiple-datasource.secondary")
public class SecondaryConfigProperty {

  private String basePackage;
  private String hibernateDialect;
  private boolean showSql;
  private boolean generateDdl;
  private String useSqlComments;
  private String formatSql;
  private String openInView;
  private String hibernateHbm2dllAuto;

}
