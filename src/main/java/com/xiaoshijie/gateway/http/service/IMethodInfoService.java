package com.xiaoshijie.gateway.http.service;

import com.xiaoshijie.gateway.strategy.StrategyContext;
import lombok.Data;

import java.util.List;

public interface IMethodInfoService {

    String METHOD_INFO_LIST = "GTW_METHOD_LIST";
    String METHOD_INFO_DETAIL = "GTW_METHOD_DETAIL";

    boolean add(MethodInfo methodInfo);

    List<MethodInfo> queryList(Integer appId);

    boolean update(MethodInfo methodInfo);

    MethodInfo find(Integer appId, String method);
    @Data
    class MethodInfo {
        Integer appId;
        String name;
        StrategyContext strategyContext;
    }

    boolean delete(MethodInfo methodInfo);
}
