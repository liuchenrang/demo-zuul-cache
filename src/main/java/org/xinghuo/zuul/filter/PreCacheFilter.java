package org.xinghuo.zuul.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.xinghuo.zuul.response.WrapperResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.List;
import java.util.Map;

public class PreCacheFilter extends CachingBaseFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private int order;
    private ZuulProperties properties;

    public PreCacheFilter(CacheManager cacheManager, int order, ZuulProperties properties) {

        super(cacheManager);


        this.order = order;
        this.properties = properties;
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
        HttpServletRequest req = ctx.getRequest();

        Map<String, ZuulProperties.ZuulRoute> routes = properties.getRoutes();
        ZuulProperties.ZuulRoute zuulRoute = routes.get("taobao-gwt-service");
        ctx.set(SERVICE_ID, zuulRoute.getServiceId());
        if (!getVerb(req).equals("GET")) {
            return false;
        }

        String serviceId = serviceId(ctx);
        Cache cache = cache(ctx);
        if ((serviceId != null) && (cache != null)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest req = ctx.getRequest();
        Cache cache = cache(ctx);
        if (cache != null) {
            String key = cacheKey(req);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                // TODO cache should probably not store HttpServletResponse
                String res = (String) valueWrapper.get();
                if (res != null) {
                    log.info("Filling response for '{}' from '{}' cache", key, cache.getName());
                    ctx.setSendZuulResponse(false);
                    ctx.setDebugRequest(true);
                    ctx.addZuulResponseHeader("xx", "yy");

                    ctx.setResponseBody(res);

                    ctx.set(CACHE_HIT, true);
                    return res;
                }
            }
        }
        ctx.set(CACHE_HIT, false);
        return null;
    }

    private boolean isGzipResponse(RequestContext ctx) {
        List<Pair<String, String>> originResponseHeaders = ctx.getOriginResponseHeaders();
        return originResponseHeaders.stream().filter(stringStringPair -> "content-encoding".equals(stringStringPair.first())).count() > 0;
    }
}
