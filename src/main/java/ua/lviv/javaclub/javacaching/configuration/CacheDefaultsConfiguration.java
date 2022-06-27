package ua.lviv.javaclub.javacaching.configuration;

import lombok.extern.slf4j.Slf4j;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class CacheDefaultsConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new SpringCache2kCacheManager()
            // Set default cache item eviction time 2s (can be set individually in .addCache[s]
            .defaultSetup(builder -> builder.expireAfterWrite(Duration.ofSeconds(2L)));
    }

}
