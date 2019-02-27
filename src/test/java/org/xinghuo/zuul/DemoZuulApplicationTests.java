package org.xinghuo.zuul;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoZuulApplicationTests {
    @Autowired
    CacheManager cacheManager;
    @Test
    public void contextLoads() {
//        cacheManager.getCache("ddd").put("xx", "yy");
//        System.out.println( cacheManager.getCache("ddd").get("xx").get() );

          int min = (int)System.currentTimeMillis()/1000/60;
        System.out.println(min % 30);
    }


}
