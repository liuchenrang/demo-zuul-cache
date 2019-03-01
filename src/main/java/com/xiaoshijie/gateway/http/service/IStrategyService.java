package com.xiaoshijie.gateway.http.service;

import com.xiaoshijie.gateway.strategy.StrategyContext;
import com.xiaoshijie.gateway.strategy.config.ApiCacheConfig;

public interface IStrategyService {
    StrategyContext queryApiCacheConfig(Integer appId, String method);
}
