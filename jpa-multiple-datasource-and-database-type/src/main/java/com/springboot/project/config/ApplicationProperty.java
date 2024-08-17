package com.springboot.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.multiple-datasource")
public class ApplicationProperty {

  private General general;

  @Getter
  @Setter
  public static class General {
    private String apiBasePath;
  }

}
