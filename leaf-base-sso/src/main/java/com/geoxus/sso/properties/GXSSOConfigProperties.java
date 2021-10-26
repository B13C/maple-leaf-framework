package com.geoxus.sso.properties;

import com.geoxus.core.framework.factory.GXYamlPropertySourceFactory;
import com.geoxus.sso.config.GXSSOConfig;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = {"classpath:/${spring.profiles.active}/sso.yml"},
        factory = GXYamlPropertySourceFactory.class,
        encoding = "utf-8",
        ignoreResourceNotFound = true)
@ConditionalOnMissingClass({"com.alibaba.nacos.api.config.ConfigFactory"})
@ConfigurationProperties(prefix = "sso")
public class GXSSOConfigProperties {
    @NestedConfigurationProperty
    private GXSSOConfig config;
}
