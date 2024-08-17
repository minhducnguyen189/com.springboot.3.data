package com.springboot.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final ApplicationProperty applicationProperty;

  @Autowired
  public WebMvcConfig(ApplicationProperty applicationProperty) {
    this.applicationProperty = applicationProperty;
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(
        this.applicationProperty.getGeneral().getApiBasePath(),
        HandlerTypePredicate.forAnnotation(RestController.class));
  }
}
