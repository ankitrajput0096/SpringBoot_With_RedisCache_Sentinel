package com.redis_cache.redis_sentinel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/* NOTE :
1) Explore ValueOperation in redisTemplate
2) Explore ListOperation in redisTemplate
 */

@Component
public class GenericRedisTemplateImpl implements GenericRedisTemplate{
    private HashOperations<String, String, String> hashOperation;

    RedisTemplate redisTemplate;

    @Autowired
    public GenericRedisTemplateImpl(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperation = this.redisTemplate.opsForHash();
    }
    public void putMap(String redisKey, String key, String data) {
        hashOperation.put(redisKey, key, data);
    }
    public String getMap(String redisKey, String key) {
        return  hashOperation.get(redisKey,key);
    }
    public Map<String, String> getMapAsAll(String redisKey) {
        return hashOperation.entries(redisKey);
    }
    public void deleteMap(String redisKey, String key) {
        hashOperation.delete(redisKey, key);
    }
}
