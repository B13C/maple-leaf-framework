package com.geoxus.core.common.config;

import cn.hutool.json.JSONUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
@ConditionalOnClass(name = {"org.redisson.Redisson"})
@ConditionalOnMissingClass(value = {"com.alibaba.nacos.api.config.ConfigFactory"})
public class GXRedissonSpringDataConfig {
    @Resource
    private GXRedissonConfig redissonConfig;

    @Resource
    private GXRedissonCacheManagerConfig redissonCacheManagerConfig;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        final Config config = JSONUtil.toBean(JSONUtil.toJsonStr(redissonConfig.getConfig()), Config.class);
        return Redisson.create(config);
    }

    @Bean("redissonSpringCacheManager")
    public RedissonSpringCacheManager cacheManager(RedissonClient redisson) {
        final Map<String, CacheConfig> config = redissonCacheManagerConfig.getConfig();
        if (config.isEmpty()) {
            return new RedissonSpringCacheManager(redisson);
        }
        return new RedissonSpringCacheManager(redisson, config);
    }
}