package com.xiaoshijie.gateway.utils;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils   {
     public  static  <T> T  deSerialize(String data, Class<T> aClass) {
        return  JSON.parseObject(data, aClass);
    }
    public static String serialize(Object o) {
        return JSON.toJSON(o).toString();
    }
    public static <T> List<T> revertList(List<String> stringList, Class<T> tClass){
        List<T>  output = new ArrayList<>(stringList.size());
        for(String data:stringList){
            output.add(deSerialize(data,tClass));
        }
        return output;
    }
}
