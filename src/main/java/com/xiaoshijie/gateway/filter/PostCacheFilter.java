package com.xiaoshijie.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import com.xiaoshijie.gateway.http.entity.ApiGate;
import com.xiaoshijie.gateway.utils.ApiGateUtils;
import com.xiaoshijie.gateway.utils.FilterUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

public class PostCacheFilter extends CachingBaseFilter  {
    private int order;

    public PostCacheFilter(CacheManager cacheManager, int order) {
        super(cacheManager);
        this.order = order;
    }
    ApplicationContext applicationContext;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return order;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return  !ctx.getBoolean(CACHE_HIT);
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        InputStream sourceInputStream = ctx.getResponseDataStream();
        try {
            if (ctx.getResponseGZipped()) {
                sourceInputStream = new GZIPInputStream(sourceInputStream);
            }
            String responseAsString = StreamUtils.copyToString(sourceInputStream, Charset.forName("UTF-8"));
            ctx.setResponseBody(responseAsString);
            ApiGate apiGate = (ApiGate) ctx.get("apiGate");
            String serviceName = (String) ctx.get("serviceName");
            String key = ApiGateUtils.getGateHashId(apiGate);
            Cache cache = cacheManager.getCache(serviceName);
            cache.put(key, responseAsString);
        } catch (IOException e) {
            log.warn("Error reading body", e);
        }

        return null;
    }

}
