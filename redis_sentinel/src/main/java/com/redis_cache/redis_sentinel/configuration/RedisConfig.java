package com.redis_cache.redis_sentinel.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisProperties;

@Configuration
public class RedisConfig {

    public final RedisSentinelConfiguration getSentinelConfig() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 10001)
                .sentinel("127.0.0.1", 10002)
                .sentinel("127.0.0.1", 10003);
        return sentinelConfig;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory(getSentinelConfig());
    }
}
