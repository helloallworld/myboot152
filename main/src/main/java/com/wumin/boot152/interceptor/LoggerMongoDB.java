package com.wumin.boot152.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerMongoDB {
    String description() default "";
    String value() default "";
}