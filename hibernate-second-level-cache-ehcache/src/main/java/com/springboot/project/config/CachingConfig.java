package com.springboot.project.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.expiry.ExpiryPolicy;
import org.ehcache.jsr107.Eh107Configuration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CachingConfig {

  private static final String MY_CACHE = "MY_CACHE";

  @Bean
  public CacheManager jCacheManager() {
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    Map<String, CacheConfiguration<?, ?>> cacheMap = new HashMap<>();

    ResourcePoolsBuilder resourcePoolsBuilder =
        ResourcePoolsBuilder.heap(100).offheap(10, MemoryUnit.MB);

    CacheConfiguration<Object, Object> cacheConfiguration =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class, Object.class, resourcePoolsBuilder)
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1)))
            .build();

    javax.cache.configuration.Configuration<Object, Object> cacheConfig =
        Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration);

    cacheManager.createCache(MY_CACHE, cacheConfig);
    return cacheManager;
  }
}
