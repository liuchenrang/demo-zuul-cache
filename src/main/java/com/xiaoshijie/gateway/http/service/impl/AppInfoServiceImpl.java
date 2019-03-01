package com.xiaoshijie.gateway.http.service.impl;

import com.xiaoshijie.gateway.http.service.IAppInfoService;
import com.xiaoshijie.gateway.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AppInfoServiceImpl implements IAppInfoService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean add(AppInfo appInfo) {
        String data = ConfigUtils.serialize(appInfo);
        String appId = appInfo.getAppId().toString();
        redisTemplate.opsForList().leftPushIfPresent(IAppInfoService.APP_INFO_LIST, appId);
        return redisTemplate.opsForHash().putIfAbsent(IAppInfoService.APP_INFO_DETAIL, appId, data);
    }

    @Override
    public List<AppInfo> queryList() {
        List<String> appIdList = redisTemplate.opsForList().range(IAppInfoService.APP_INFO_LIST, 0, 10);
        if (appIdList != null) {
            List<AppInfo> appInfoList = new ArrayList<>(appIdList.size());
            for (String appId : appIdList) {
                String o = (String) redisTemplate.opsForHash().get(IAppInfoService.APP_INFO_DETAIL, appId);
                appInfoList.add(ConfigUtils.deSerialize(o, AppInfo.class));
            }
            return appInfoList;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(AppInfo appInfo) {
        String data = ConfigUtils.serialize(appInfo);
        String appId = appInfo.getAppId().toString();
        redisTemplate.opsForList().leftPushIfPresent(IAppInfoService.APP_INFO_LIST, appId);
        redisTemplate.opsForHash().put(IAppInfoService.APP_INFO_DETAIL, appId, data);
        return true;
    }

    @Override
    public boolean delete(AppInfo appInfo) {
        String  appId = appInfo.getAppId().toString();
        redisTemplate.opsForList().remove(IAppInfoService.APP_INFO_LIST, 0, appId);
        redisTemplate.opsForHash().delete(IAppInfoService.APP_INFO_DETAIL, appId);
        return true;
    }

    @Override
    public AppInfo find(Integer appId) {
        String o = (String) redisTemplate.opsForHash().get(IAppInfoService.APP_INFO_DETAIL, appId.toString());
        return ConfigUtils.deSerialize(o, AppInfo.class);
    }
}
