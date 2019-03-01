package com.xiaoshijie.gateway.strategy.config;

import lombok.Data;

@Data
public class StatsConfig extends BaseConfig{
    /**
     * 是否开启统计
     */
    boolean stats;
}
