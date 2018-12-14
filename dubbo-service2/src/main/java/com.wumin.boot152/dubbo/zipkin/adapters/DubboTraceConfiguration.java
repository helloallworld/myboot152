package com.wumin.boot152.dubbo.zipkin.adapters;

import com.github.kristofa.brave.Brave;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Brave.class)
public class DubboTraceConfiguration {
    @Bean
    public ApplicationContextAware holder() {
        return new ApplicationContextHolder();
    }
}