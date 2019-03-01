package com.xiaoshijie.gateway.event;

import com.netflix.zuul.context.RequestContext;
import org.springframework.context.ApplicationEvent;

public class QueryEvent extends ApplicationEvent {
    private RequestContext ctx;
    private String content;
    public QueryEvent(Object source, RequestContext requestContext,String content) {
        super(source);
        ctx = requestContext;
        this.content = content;
    }

    public RequestContext getCtx() {
        return ctx;
    }

    public String getContent() {
        return content;
    }
}
