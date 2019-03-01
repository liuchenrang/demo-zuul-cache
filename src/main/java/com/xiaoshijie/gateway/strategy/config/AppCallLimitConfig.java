package com.xiaoshijie.gateway.strategy.config;

public class AppCallLimitConfig extends BaseConfig {
    /**
     * 0 失败报错 1 取缓存失败并报错
     */
    int status;
}
