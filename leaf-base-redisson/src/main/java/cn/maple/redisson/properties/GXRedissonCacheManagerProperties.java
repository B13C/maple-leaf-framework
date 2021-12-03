package cn.maple.redisson.properties;

import cn.maple.core.framework.factory.GXYamlPropertySourceFactory;
import lombok.Data;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Configuration
@SuppressWarnings("all")
@ConditionalOnClass(name = {"org.redisson.Redisson"})
@PropertySource(value = {"classpath:/redisson-cache-config.yml"},
        factory = GXYamlPropertySourceFactory.class,
        encoding = "utf-8",
        ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "redisson")
public class GXRedissonCacheManagerProperties {
    private Map<String, CacheConfig> config = new LinkedHashMap<>();
}
