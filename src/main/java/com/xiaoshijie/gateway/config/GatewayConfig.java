package com.xiaoshijie.gateway.config;

import com.xiaoshijie.gateway.serialize.FastjsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class GatewayConfig {

    @Bean
    CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        RedisSerializationContext.SerializationPair<String> keySerializationPair = RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer());

        RedisSerializationContext.SerializationPair<String> valueStringSerializationPair =  RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer());
        redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(keySerializationPair).serializeValuesWith(valueStringSerializationPair);

        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();
        objectObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        objectObjectRedisTemplate.setDefaultSerializer(stringRedisSerializer);
        return objectObjectRedisTemplate;
    }
 }

