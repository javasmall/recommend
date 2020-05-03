package com.bigsai.recommend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

public class dataUtils {

    public static  String mapToString(List<Map.Entry<String,Double>> contentTfidf){
        return JSON.toJSONString(contentTfidf);
    };
    public static  List<Map.Entry<String,Double>> stringToMap(String listStr)
    {
        List<Map.Entry<String,Double>>list= JSON.parseObject(listStr,new TypeReference<List<Map.Entry<String,Double>>>(){});
        return list;
    }
}
