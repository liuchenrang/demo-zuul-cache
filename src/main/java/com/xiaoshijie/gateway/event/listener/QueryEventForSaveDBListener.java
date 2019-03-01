package com.xiaoshijie.gateway.event.listener;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshijie.gateway.event.QueryEvent;
import com.xiaoshijie.gateway.http.entity.ApiGate;
import com.xiaoshijie.gateway.http.service.IApiGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueryEventForSaveDBListener implements ApplicationListener<QueryEvent> {
    @Autowired
    IApiGateService apiGateService;
    @Override
    public void onApplicationEvent(QueryEvent event) {
        ApiGate apiGate = (ApiGate) event.getCtx().get("apiGate");
        QueryWrapper<ApiGate> apiGateWrapper = new QueryWrapper<>();
        apiGateWrapper.eq("hashid", apiGate.getHashid());
        ApiGate one = apiGateService.getOne(apiGateWrapper);
        if (one == null) {
            apiGateService.save(apiGate);
        }else{
            apiGate.setId(one.getId());
            apiGateService.updateById(apiGate);
        }
    }
}