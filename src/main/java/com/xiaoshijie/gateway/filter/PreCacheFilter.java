package com.xiaoshijie.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import com.xiaoshijie.gateway.http.entity.ApiGate;
import com.xiaoshijie.gateway.strategy.StrategyContext;
import com.xiaoshijie.gateway.http.service.IStrategyService;
import com.xiaoshijie.gateway.utils.ApiGateUtils;
import com.xiaoshijie.gateway.utils.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import javax.servlet.http.HttpServletRequest;

public class PreCacheFilter extends CachingBaseFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private int order;
    private IStrategyService strategyService;
    public PreCacheFilter(CacheManager cacheManager, int order, ZuulProperties properties) {

        super(cacheManager);
        this.order = order;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return order;
    }


    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ApiGate apiGate = FilterUtils.buildApiGate(ctx);
        ctx.set("apiGate", apiGate);
        ctx.set("serviceName", FilterUtils.getServiceName(ctx));
        StrategyContext context = strategyService.queryApiCacheConfig(apiGate.getAppid(), apiGate.getMethod());
        if (context != null && context.getApiCacheConfig() != null && context.getApiCacheConfig().getStatus()) {
            return true;
        }
        return false;

    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        Cache cache = cacheManager.getCache(FilterUtils.getServiceName(ctx));
        if (cache != null) {
            ApiGate apiGate = (ApiGate) ctx.get("apiGate");
            String key = ApiGateUtils.getGateHashId(apiGate);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                // TODO cache should probably not store HttpServletResponse
                String res = (String) valueWrapper.get();
                if (res != null) {
                    ctx.setSendZuulResponse(false);
                    ctx.setDebugRequest(true);
                    ctx.setResponseBody(res);
                    ctx.set(CACHE_HIT, true);
                    return res;
                }
            }
        }
        ctx.set(CACHE_HIT, false);
        return null;
    }

}
