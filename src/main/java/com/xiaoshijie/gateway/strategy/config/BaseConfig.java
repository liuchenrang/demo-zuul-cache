package com.xiaoshijie.gateway.strategy.config;

import com.alibaba.fastjson.JSON;
import com.xiaoshijie.gateway.strategy.ConfigSerialize;

public class BaseConfig implements ConfigSerialize {
    @Override
    public String serialize() {
        return JSON.toJSON(this).toString();
    }
}
