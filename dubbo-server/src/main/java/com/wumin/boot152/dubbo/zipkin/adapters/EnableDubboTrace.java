package com.wumin.boot152.dubbo.zipkin.adapters;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DubboTraceConfiguration.class)
public @interface EnableDubboTrace {
}
