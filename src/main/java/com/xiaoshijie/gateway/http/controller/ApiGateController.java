package com.xiaoshijie.gateway.http.controller;


import com.xiaoshijie.gateway.http.RestResultBuilder;
import com.xiaoshijie.gateway.http.service.IAppInfoService;
import com.xiaoshijie.gateway.http.service.IMethodInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author duoduo
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/v1/")
public class ApiGateController {
    @Autowired
    IAppInfoService appInfoService;
    @Autowired
    IMethodInfoService methodInfoService;
    @RequestMapping("app")
    Object queryApp(){
        List<IAppInfoService.AppInfo> appInfos = appInfoService.queryList();
        return RestResultBuilder.createRestTrueResult().setData(appInfos);
    }
    @RequestMapping("app/create")
    Object createApp(@RequestBody IAppInfoService.AppInfo appInfo){
        appInfoService.add(appInfo);
        return new RestResultBuilder(true,200);
    }
    @RequestMapping("app/update")
    Object updateApp(@RequestBody IAppInfoService.AppInfo appInfo){
        appInfoService.update(appInfo);
        return new RestResultBuilder(true,200);
    }
    @RequestMapping("app/detail")
    Object detailApp(@RequestParam Integer appId){
        IAppInfoService.AppInfo appInfo = appInfoService.find(appId);
        return new RestResultBuilder(true,200, appInfo);
    }
    @RequestMapping("app/delete")
    Object deleteApp(@RequestParam Integer appId){
        IAppInfoService.AppInfo appInfo = new IAppInfoService.AppInfo();
        appInfo.setAppId(appId);
        appInfoService.delete(appInfo);
        return new RestResultBuilder(true,200, appInfo);
    }


    @RequestMapping("method")
    Object queryMethod(@RequestParam Integer appId){
        List<IMethodInfoService.MethodInfo> list = methodInfoService.queryList(appId);
        return new RestResultBuilder(true,200, list);

    }
    @RequestMapping("method/create")
    Object createMethod(@RequestBody IMethodInfoService.MethodInfo methodInfo){
        methodInfoService.add(methodInfo);
        return new RestResultBuilder(true,200);
    }
    @RequestMapping("method/update")
    Object updateMethod(@RequestBody IMethodInfoService.MethodInfo methodInfo){
        methodInfoService.update(methodInfo);
        return new RestResultBuilder(true,200);
    }
    @RequestMapping("method/delete")
    Object deleteMethod(@RequestParam Integer appId,@RequestParam String name){
        IMethodInfoService.MethodInfo methodInfo = new IMethodInfoService.MethodInfo();
        methodInfo.setAppId(appId);
        methodInfo.setName(name);
        methodInfoService.delete(methodInfo);
        return new RestResultBuilder(true,200);
    }
    @RequestMapping("method/detail")
    Object detailMethod(@RequestParam Integer appId,@RequestParam String name){
        IMethodInfoService.MethodInfo methodInfo = methodInfoService.find(appId, name);
        return new RestResultBuilder(true,200, methodInfo);

    }
}
