package com.redis_cache.redis_sentinel.configuration;

public interface GenericRedisValueOpsTemplate {
    void putMap(String key, String data);
    void putMapWithTimeOut(String key, String data);
    String getMap(String key);
}
