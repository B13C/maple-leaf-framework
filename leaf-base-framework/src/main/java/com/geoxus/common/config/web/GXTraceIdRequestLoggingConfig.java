package com.geoxus.common.config.web;

import com.geoxus.common.filter.GXTraceIdRequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GXTraceIdRequestLoggingConfig {
    @Bean
    public GXTraceIdRequestLoggingFilter traceIdRequestLoggingFilter() {
        return new GXTraceIdRequestLoggingFilter();
    }
}