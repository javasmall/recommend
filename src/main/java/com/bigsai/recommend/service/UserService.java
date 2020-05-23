package com.bigsai.recommend.service;

import com.bigsai.recommend.dao.newsTopkWeightMapper;
import com.bigsai.recommend.dao.userMapper;
import com.bigsai.recommend.pojo.newsTopkWeight;
import com.bigsai.recommend.utils.DataChangeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired(required = false)
    newsTopkWeightMapper newsTopkWeightMapper;
    @Autowired(required = false)
    userMapper userMapper;
    /**
     * 每阅读该篇文章，说明用户对该篇文章有兴趣，用户喜欢关键词进行添加.叠加到用户喜欢列表中
     * @param newsId 新闻的id
     * @param username 用户昵称
     */
    public void readAriticle(String newsId,String username)
    {
        double mut=1;
        updateAriticle(newsId,username,mut);
    }

    /**
     * 点赞会以普通阅读的2.5倍去存储该篇文章的tf-idf到userlist中。
     * @param newsId
     * @param username
     */
    public  void starAriticle(String newsId,String username)
    {
        double mut=2.5;
        updateAriticle(newsId,username,mut);
    }

    /**
     * 用户喜爱列表的参数需要叠加，该函数被starAticle 和readAriticle使用。不过倍数不同(主要为了代码复用)
     * @param newsId
     * @param username
     * @param mut 倍数
     */
    public void updateAriticle(String newsId,String username,double mut)
    {
        newsTopkWeight newsTopkWeight=newsTopkWeightMapper.getTopkWeightById(newsId);
        List<Map.Entry<String,Double>>newsEntries= DataChangeUtils.stringToListMap(newsTopkWeight.getTopk());
        String userPreferString=userMapper.getUserpre(username);
        Map<String,Double>userPreferMap;
        if(userPreferString==null||"".equals(userPreferString))//无喜好列表
            userPreferMap=new HashMap<>();
        else
            userPreferMap=DataChangeUtils.stringToMap(userPreferString);
        for(Map.Entry<String,Double>entry:newsEntries)
        {
            Object object=entry.getValue();
            double value=Double.parseDouble(object.toString());
            if(userPreferMap.containsKey(entry.getKey()))
            {
                userPreferMap.put(entry.getKey(),value*mut+userPreferMap.get(entry.getKey()));
            }
            else
                userPreferMap.put(entry.getKey(),value  *mut);
        }
        //转换成字符串进行更新
        userPreferString=DataChangeUtils.mapToString(userPreferMap);
        userMapper.updatePreListbyid(userPreferString,username);
    }

}
