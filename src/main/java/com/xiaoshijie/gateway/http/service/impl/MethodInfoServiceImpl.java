package com.xiaoshijie.gateway.http.service.impl;

import com.xiaoshijie.gateway.http.service.IAppInfoService;
import com.xiaoshijie.gateway.http.service.IMethodInfoService;
import com.xiaoshijie.gateway.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MethodInfoServiceImpl implements IMethodInfoService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean add(MethodInfo methodInfo) {
        String data = ConfigUtils.serialize(methodInfo);
        String keyApp = builderListMethod(methodInfo.getAppId(), "");
        String keyMethod = builderHashMethod(methodInfo.getAppId(), "");
        redisTemplate.opsForList().leftPushIfPresent(keyApp, methodInfo.getName());
        return redisTemplate.opsForHash().putIfAbsent(keyMethod, methodInfo.getName(), data);
    }
    private String builderListMethod(Integer appId, String method){
        return String.format("%s.%s.%s",IMethodInfoService.METHOD_INFO_LIST, appId, method);
    }
    private String builderHashMethod(Integer appId, String method){
        return String.format("%s.%s.%s",IMethodInfoService.METHOD_INFO_DETAIL, appId, method);
    }
    @Override
    public List<MethodInfo> queryList(Integer appId) {
        String keyApp = builderListMethod(appId, "");
        String keyMethod = builderHashMethod(appId, "");
        List<String> methodInfoList = redisTemplate.opsForList().range(keyApp, 0, 300);
        if (methodInfoList != null) {
            List<IMethodInfoService.MethodInfo> infoList = new ArrayList<>(methodInfoList.size());
            for (String methodName : methodInfoList) {

                String o = (String) redisTemplate.opsForHash().get(keyMethod, methodName);
                infoList.add(ConfigUtils.deSerialize(o, IMethodInfoService.MethodInfo.class));
            }
            return infoList;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(MethodInfo methodInfo) {

        String keyApp = builderListMethod(methodInfo.getAppId(), "");
        String keyMethod = builderHashMethod(methodInfo.getAppId(), "");

        String data = ConfigUtils.serialize(methodInfo);
        redisTemplate.opsForList().leftPushIfPresent(keyApp, methodInfo.getName());
        redisTemplate.opsForHash().put(keyMethod, methodInfo.getName(), data);
        return true;
    }

    @Override
    public boolean delete(MethodInfo methodInfo) {
        String keyApp = builderListMethod(methodInfo.getAppId(), "");
        String keyMethod = builderHashMethod(methodInfo.getAppId(), "");

        redisTemplate.opsForList().remove(keyApp, 0, methodInfo.getName());
        redisTemplate.opsForHash().delete(keyMethod, methodInfo.getName());
        return true;
    }

    @Override
    public MethodInfo find(Integer appId, String method) {
        String keyMethod = builderHashMethod(appId, "");

        String data = (String) redisTemplate.opsForHash().get(keyMethod, method);
        if (data != null){
            return ConfigUtils.deSerialize(data, MethodInfo.class);
        }
        return null;
    }
}
