package com.xiaoshijie.gateway.serialize;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author chen
 */
public class FastjsonRedisSerializer<T> implements RedisSerializer<T> {
    private Class<T> clazz;

    public FastjsonRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return JSON.toJSON(t).toString().getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        String text = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(text, clazz);
    }
}
