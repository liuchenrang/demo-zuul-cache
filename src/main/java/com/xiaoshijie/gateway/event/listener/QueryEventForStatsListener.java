package com.xiaoshijie.gateway.event.listener;

import com.xiaoshijie.gateway.event.QueryEvent;
import org.springframework.context.ApplicationListener;

public class QueryEventForStatsListener implements ApplicationListener<QueryEvent> {

    @Override
    public void onApplicationEvent(QueryEvent event) {

    }
}