package cn.maple.mongodb.datasource.properties.nacos;

import cn.maple.mongodb.datasource.properties.GXMongoDynamicDataSourceProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * 多数据源属性
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Component
@ConditionalOnClass(name = {"com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties"})
@NacosConfigurationProperties(groupId = "${nacos.config.group:DEFAULT_GROUP}", prefix = "mongodb", dataId = "mongodb.yml", autoRefreshed = true, type = ConfigType.YAML)
public class GXNacosMongoDynamicDataSourceProperties extends GXMongoDynamicDataSourceProperties {
    public GXNacosMongoDynamicDataSourceProperties() {
        log.info("MongoDB数据源的配置使用的是NACOS配置");
    }
}