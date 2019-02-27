package org.xinghuo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

public class PostCacheFilter extends CachingBaseFilter {
    private int order;

    public PostCacheFilter(CacheManager cacheManager, int order) {
        super(cacheManager);
        this.order = order;
    }


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
        HttpServletResponse res = ctx.getResponse();

        if (isSuccess(res)) {
            // Store only successful responses
            InputStream sourceInputStream = ctx.getResponseDataStream();
            try {
                // Uncompress and transform the response
                if (ctx.getResponseGZipped()) {
                     sourceInputStream = new GZIPInputStream(sourceInputStream);
                }
                String responseAsString = StreamUtils.copyToString(sourceInputStream, Charset.forName("UTF-8"));
                // Do want you want with your String response
                // Replace the response with the modified object
                ctx.setResponseBody(responseAsString);


                Cache cache = cache(ctx);
                if (cache != null) {
//                    // TODO cache should probably not store HttpServletResponse
                    String key = cacheKey(req);
//                    ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(res);
//                    try {
//                        cachingResponseWrapper.copyBodyToResponse();
//                        String o1 = new String(cachingResponseWrapper.getContentAsByteArray());
                     cache.put(key, responseAsString);
//                        log.info("Cached successful response for '{}' into '{}' cache", key, cache.getName());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

            } catch (IOException e) {
//                logger.warn("Error reading body", e);
            }


        }

        return null;
    }

    private boolean isSuccess(HttpServletResponse res) {
        return (res != null) && (res.getStatus() < 300);
    }

}
