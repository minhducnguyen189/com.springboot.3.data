package com.springboot.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HibernateSecondLevelCatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(HibernateSecondLevelCatchApplication.class, args);
    }
}