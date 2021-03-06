package cn.maple.shiro.config;

import cn.maple.core.framework.web.config.GXWebMvcConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@Slf4j
public class GXShiroWebMvcInterceptorConfig extends GXWebMvcConfigurer {
    @Override
    protected void registerCustomerInterceptors(InterceptorRegistry registry) {

    }
}