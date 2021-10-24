package com.geoxus.core.framework.interceptor;

import com.geoxus.core.framework.web.interceptor.GXWebMvcInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class GXCommonWebMvcInterceptor extends GXWebMvcInterceptor {
    @Resource
    private GXTraceIdInterceptor traceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(traceIdInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }
}