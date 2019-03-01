package com.xiaoshijie.gateway.http.service;

import com.xiaoshijie.gateway.strategy.StrategyContext;
import lombok.Data;

import java.util.List;

public interface IAppInfoService {
    String APP_INFO_LIST = "GTW_APP_INFO";
    String APP_INFO_DETAIL = "GTW_APP_DETAIL";
    boolean add(AppInfo appInfo);

    List<AppInfo> queryList();

    boolean update(AppInfo appInfo);

    @Data
    class AppInfo {
        Integer apiCount;
        Integer appId;
        StrategyContext strategyContext;
    }

    boolean delete(AppInfo appInfo);

    AppInfo find(Integer appId);
}
