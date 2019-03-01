package com.xiaoshijie.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xiaoshijie.gateway.event.QueryEvent;
import com.xiaoshijie.gateway.utils.FilterUtils;
import org.springframework.context.ApplicationContext;
import javax.servlet.http.HttpServletResponse;

public class FinallyPostFilter extends ZuulFilter {

    private int order;
    private ApplicationContext applicationContext;

    public FinallyPostFilter(ApplicationContext ctx, int order) {
        applicationContext = ctx;
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
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse res = ctx.getResponse();
        applicationContext.publishEvent(new QueryEvent(this, ctx, FilterUtils.getResponseContent(ctx)));

        return null;
    }

    private boolean isSuccess(HttpServletResponse res) {
        return (res != null) && (res.getStatus() < 300);
    }

}
