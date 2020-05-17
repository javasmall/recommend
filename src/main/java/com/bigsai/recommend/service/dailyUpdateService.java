package com.bigsai.recommend.service;
import com.bigsai.recommend.dao.newsTopkWeightMapper;
import com.bigsai.recommend.dao.userMapper;
import com.bigsai.recommend.pojo.users;
import com.bigsai.recommend.utils.DataChangeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日常更新，新闻热度更新，用户喜好关键词衰退
 */
@Service
public class dailyUpdateService {
    private Logger log= LoggerFactory.getLogger(dailyUpdateService.class);

    @Autowired(required = false)
    newsTopkWeightMapper newsTopkWeightMapper;
    @Autowired(required = false)
    userMapper userMapper;


    /**
     * 每日对新闻weight进行衰减，衰减频率为0.7每日。2日为0.49
     */
    public void newsWeightDeclile()
    {
        List<Map<String,Object>> newsIdAndWeightList=newsTopkWeightMapper.getAllNewsIdAndWeight();
        Map<String,Double>newsidAndWeghtMap=new HashMap<>();
        for(Map<String,Object>map:newsIdAndWeightList)
        {
            String id=(String) map.get("news_id");
            Object object=map.get("weight");
            Double weight=Double.parseDouble(object.toString());
            weight*=0.7;
            newsidAndWeghtMap.put(id,weight);

        }
        newsTopkWeightMapper.updateWeightByNewsId(newsidAndWeghtMap);

    }

    public  void  userPreferDecline()
    {
        double declie=0.7;
        List<users>usersList=userMapper.getLastDayUsers();
        for(users u:usersList)
        {
            if(u.getPreList()==null||u.getPreList().equals(""))
                continue;
           Map<String,Double>userPreferMap= DataChangeUtils.stringToMap(u.getPreList());
            for(String key:userPreferMap.keySet())
            {
                userPreferMap.put(key,userPreferMap.get(key)*declie);
            }
            u.setPreList(DataChangeUtils.mapToString(userPreferMap));
        }
        userMapper.updatePreList(usersList);
    }
}
