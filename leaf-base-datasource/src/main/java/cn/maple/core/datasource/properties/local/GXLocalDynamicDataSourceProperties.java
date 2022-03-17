package cn.maple.core.datasource.properties.local;

import cn.maple.core.datasource.properties.GXDynamicDataSourceProperties;
import cn.maple.core.framework.factory.GXYamlPropertySourceFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 多数据源属性
 */
@Data
@Slf4j
@Component
@EqualsAndHashCode(callSuper = true)
@ConditionalOnMissingClass({"com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties"})
@PropertySource(value = {"classpath:/ymls/${spring.profiles.active}/datasource.yml"}, factory = GXYamlPropertySourceFactory.class, ignoreResourceNotFound = false)
@ConfigurationProperties(prefix = "dynamic")
public class GXLocalDynamicDataSourceProperties extends GXDynamicDataSourceProperties {
    public GXLocalDynamicDataSourceProperties() {
        log.info("MySQL数据源的配置使用的是本地配置");
    }
}