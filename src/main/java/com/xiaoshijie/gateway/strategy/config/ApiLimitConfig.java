package com.xiaoshijie.gateway.strategy.config;

public class ApiLimitConfig extends BaseConfig {
    /**
     * 使用量
     */
    Integer limit;
    /**
     * 超过是否报警
     */
    Boolean report;
    /**
     * 是否继续调用
     */
    Boolean continueCall;
    /**
     * 优先级
     */
    Integer priority;
}
