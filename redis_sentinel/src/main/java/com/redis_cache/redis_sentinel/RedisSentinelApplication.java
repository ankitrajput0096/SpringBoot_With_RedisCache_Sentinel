package com.redis_cache.redis_sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@SpringBootApplication
@EnableCaching          // Enabling caching in this project
public class RedisSentinelApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisSentinelApplication.class, args);
	}
}
