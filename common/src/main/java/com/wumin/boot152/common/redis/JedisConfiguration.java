package com.wumin.boot152.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class JedisConfiguration {
    @Autowired
    RedisProperties redisConfig;

    /**
     * 创建jedis连接池，因为@EnableRedisHttpSession中也会从jedis连接池中获取连接
     */
    @Bean
    public ShardedJedisPool convertJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPool().getMaxWait());
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        JedisShardInfo jedisShardInfo= new JedisShardInfo(redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout());
        jedisShardInfo.setPassword(redisConfig.getPassword());
        jedisShardInfoList.add(jedisShardInfo);
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
    }
}
