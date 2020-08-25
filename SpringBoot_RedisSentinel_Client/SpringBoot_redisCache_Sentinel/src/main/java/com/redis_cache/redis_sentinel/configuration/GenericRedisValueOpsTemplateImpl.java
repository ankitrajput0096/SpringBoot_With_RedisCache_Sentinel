package com.redis_cache.redis_sentinel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class GenericRedisValueOpsTemplateImpl
        implements GenericRedisValueOpsTemplate{
    private ValueOperations<String, String> valueOperations;

    RedisTemplate redisTemplate;

    @Autowired
    public GenericRedisValueOpsTemplateImpl(
            RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }
    public void putMap(String key, String data) {
        this.valueOperations.set(key, data);
    }
    public void putMapWithTimeOut(String key, String data) {
        this.valueOperations.set(key, data, Duration.ofHours(12));
    }
    public String getMap(String key) {
        return  valueOperations.get(key);
    }

}
