package org.xinghuo.zuul;

import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.xinghuo.zuul.filter.PostCacheFilter;
import org.xinghuo.zuul.filter.PreCacheFilter;

import java.util.Collections;

@EnableZuulProxy
@SpringBootApplication
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
    public CacheManager cacheManager(){
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache("opentao")));

        return simpleCacheManager;
    }
}
