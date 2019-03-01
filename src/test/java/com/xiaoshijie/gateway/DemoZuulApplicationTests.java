package com.xiaoshijie.gateway;

import com.xiaoshijie.gateway.http.entity.ApiGate;
import com.xiaoshijie.gateway.http.service.IApiGateService;
import com.xiaoshijie.gateway.utils.ApiGateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoZuulApplicationTests {
    @Autowired
    CacheManager cacheManager;
    @Test
    public void contextLoads() {
        Cache ddd = cacheManager.getCache("ddd");

        System.out.println( ddd.get("xx").get() );

    }
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Test
    public void test(){
        redisTemplate.opsForList().leftPush("list", "11");
    }
    @Autowired
    IApiGateService apiGateService;
    @Test
    public void testDao(){
        ApiGate gate = new ApiGate();
        gate.setAppid(11111);
        gate.setType(1);
        gate.setMethod("taobao.item.get");
        gate.setParams("12312312");
        String gateHashId = ApiGateUtils.getGateHashId(gate);
        gate.setHashid(gateHashId);
        System.out.println(gateHashId);
        apiGateService.save(gate);
    }

}
