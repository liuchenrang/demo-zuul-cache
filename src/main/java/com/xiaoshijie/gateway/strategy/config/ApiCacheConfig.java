package com.xiaoshijie.gateway.strategy.config;

import com.xiaoshijie.gateway.strategy.ConfigSerialize;
import lombok.Data;

@Data
public class ApiCacheConfig extends BaseConfig{
        int cacheTime;
        Boolean status;
}
