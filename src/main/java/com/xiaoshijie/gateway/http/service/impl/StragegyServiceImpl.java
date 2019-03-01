package com.xiaoshijie.gateway.http.service.impl;

import com.xiaoshijie.gateway.http.service.IMethodInfoService;
import com.xiaoshijie.gateway.http.service.IStrategyService;
import com.xiaoshijie.gateway.strategy.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StragegyServiceImpl implements IStrategyService {
    @Autowired
    IMethodInfoService methodInfoService;
    @Override
    public StrategyContext queryApiCacheConfig(Integer appId, String method) {
        IMethodInfoService.MethodInfo methodInfo = methodInfoService.find(appId, method);
        if (methodInfo != null) {
            return methodInfo.getStrategyContext();
        }
        return null;
    }
}
