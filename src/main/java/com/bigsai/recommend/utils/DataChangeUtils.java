package com.bigsai.recommend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

public class DataChangeUtils {


    /**
     * Map<String,Double> => String转化
     */
     public static  String mapToString(Map<String,Double>map)
     {
         return  JSON.toJSONString(map);
     }

     public static Map<String,Double> stringToMap(String mapString)
     {
         Map<String,Double> map = JSON.parseObject(mapString,new TypeReference<Map<String,Double>>(){});
         return map;
     }



    /**
     * newsTopk中的相互转化
     * @param contentTfidf
     * @return
     */
    public static  String ListmapToString(List<Map.Entry<String,Double>> contentTfidf){
        return JSON.toJSONString(contentTfidf);
    }
    public static  List<Map.Entry<String,Double>> stringToListMap(String listStr)
    {
        List<Map.Entry<String,Double>>list= JSON.parseObject(listStr,new TypeReference<List<Map.Entry<String,Double>>>(){});
        return list;
    }
}
