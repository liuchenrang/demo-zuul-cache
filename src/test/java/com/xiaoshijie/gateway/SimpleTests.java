package com.xiaoshijie.gateway;

import com.sun.org.glassfish.external.statistics.Stats;
import com.xiaoshijie.gateway.http.service.IAppInfoService;
import com.xiaoshijie.gateway.http.service.IMethodInfoService;
import com.xiaoshijie.gateway.strategy.StrategyContext;
import com.xiaoshijie.gateway.strategy.config.ApiCacheConfig;
import com.xiaoshijie.gateway.strategy.config.StatsConfig;
import com.xiaoshijie.gateway.utils.ConfigUtils;
import org.junit.Test;

public class SimpleTests {
    @Test
    public void testConfigEncode() {
        StatsConfig statsConfig = new StatsConfig();
        statsConfig.setStats(true);
        String serialize = statsConfig.serialize();
        System.out.println(serialize);
        StatsConfig config = ConfigUtils.deSerialize(serialize, StatsConfig.class);
        System.out.println(config.isStats());
    }
    @Test
    public void testRestAppInfo(){
        IAppInfoService.AppInfo appInfo = new IAppInfoService.AppInfo();
        appInfo.setAppId(1231235);
        appInfo.setApiCount(2000000);
        StrategyContext context = new StrategyContext();
        appInfo.setStrategyContext(context);
        ApiCacheConfig apiCacheConfig = new ApiCacheConfig();
        apiCacheConfig.setStatus(true);
        apiCacheConfig.setCacheTime(100);
        context.setApiCacheConfig(apiCacheConfig);

        System.out.println(ConfigUtils.serialize(appInfo));


    }
    @Test
    public void testRestMethodInfo(){
        IMethodInfoService.MethodInfo methodInfo = new IMethodInfoService.MethodInfo();
        methodInfo.setAppId(1231235);
        methodInfo.setName("taobao.item.get");
        StrategyContext context = new StrategyContext();
        methodInfo.setStrategyContext(context);
        ApiCacheConfig apiCacheConfig = new ApiCacheConfig();
        apiCacheConfig.setStatus(true);
        apiCacheConfig.setCacheTime(100);
        context.setApiCacheConfig(apiCacheConfig);

        System.out.println(ConfigUtils.serialize(methodInfo));


    }


}
