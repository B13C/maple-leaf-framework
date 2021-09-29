package com.geoxus.common.properties;

import com.geoxus.common.factory.GXYamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@SuppressWarnings("all")
@ConditionalOnMissingClass(value = {"com.alibaba.nacos.api.config.ConfigFactory"})
@PropertySource(value = "classpath:/ymls/${spring.profiles.active}/common.yml",
        factory = GXYamlPropertySourceFactory.class,
        encoding = "utf-8",
        ignoreResourceNotFound = true)
public class GXCommonProperties {
}
