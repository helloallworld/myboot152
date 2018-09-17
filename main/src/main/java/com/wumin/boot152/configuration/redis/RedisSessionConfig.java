package com.wumin.boot152.configuration.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 将redis和http的session交给redis
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 2880 * 60)
public class RedisSessionConfig {
}
