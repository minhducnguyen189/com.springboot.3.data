package com.springboot.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HibernateSecondLevelCacheJCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(HibernateSecondLevelCacheJCacheApplication.class, args);
    }
}