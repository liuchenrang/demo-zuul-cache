package com.xiaoshijie.gateway;

import com.netflix.zuul.ZuulFilter;
import com.xiaoshijie.gateway.filter.FinallyPostFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.xiaoshijie.gateway.filter.PostCacheFilter;
import com.xiaoshijie.gateway.filter.PreCacheFilter;
import java.util.Collections;
@EnableZuulProxy
@SpringBootApplication
@MapperScan("com.xiaoshijie.gateway.http.mapper")
public class DemoZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoZuulApplication.class, args);
    }

    @Bean
    public ZuulFilter cachePostFilter(CacheManager cacheManager) {
        return new PostCacheFilter(cacheManager, 1);
    }

    @Bean
    public ZuulFilter cachePreFilter(CacheManager cacheManager, ZuulProperties zuulProperties) {
        return new PreCacheFilter(cacheManager, 99, zuulProperties);
    }

    @Bean
    public ZuulFilter finallyFilter(ApplicationContext applicationContext) {
        return new FinallyPostFilter(applicationContext, 2000);
    }
}
