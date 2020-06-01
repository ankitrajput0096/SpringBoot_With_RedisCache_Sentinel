package com.redis_cache.redis_sentinel.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/* NOTE :
1) Explore ValueOperation in redisTemplate
2) Explore ListOperation in redisTemplate
 */

public interface GenericRedisTemplate {
    public void putMap(String redisKey, String key, String data);
    public String getMap(String redisKey, String key);
    public Map<String, String> getMapAsAll(String redisKey);
    public void deleteMap(String redisKey, String key);
}
