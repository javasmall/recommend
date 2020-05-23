package com.bigsai.recommend.service;

import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.dao.newsTopkWeightMapper;
import com.bigsai.recommend.dao.userMapper;
import com.bigsai.recommend.pojo.newsTopkWeight;
import com.bigsai.recommend.pojo.recommendPojo;
import com.bigsai.recommend.tf.Recommend;
import com.bigsai.recommend.tf.TopkWord;
import com.bigsai.recommend.utils.DataChangeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {
    private Logger log = LoggerFactory.getLogger(NewsService.class);
    @Autowired(required = false)
    private newsMapper newsMapper;
    @Autowired(required = false)
    private newsTopkWeightMapper newsTopkWeightMapper;
    @Autowired(required = false)
    userMapper userMapper;


    @Autowired(required = false)
    private TopkWord topKword;

    @Autowired(required = false)
    Recommend recommend;


    //插入、更新topK
    public void insertorUpdateTopkWeight(newsTopkWeight newsTopkWeight)
    {
        newsTopkWeight nt1=newsTopkWeightMapper.getTopkWeightById(newsTopkWeight.getNewsId());
        if(nt1==null)
            newsTopkWeightMapper.insertNewsTopkWeight(newsTopkWeight);
        else
            newsTopkWeightMapper.updateTopkWeightById(newsTopkWeight);
    }
    public List<recommendPojo> recommendByusername(String username)
    {
        String userpre=userMapper.getUserpre(username);
        List<Map.Entry<String,Double>>preferMapEntry=new ArrayList<>();

        Map<String,Double>userpreferMap=DataChangeUtils.stringToMap(userpre);
        for(Map.Entry<String,Double> m:userpreferMap.entrySet())
        {
            preferMapEntry.add(m);
        }
        return recommendCount(preferMapEntry);

    }
    public List<recommendPojo> recommendBynewsId(String newsId)
    {
        newsTopkWeight newsTopkWeight=newsTopkWeightMapper.getTopkWeightById(newsId);
        List<Map.Entry<String,Double>>topk= DataChangeUtils.stringToListMap(newsTopkWeight.getTopk());
        return recommendCount(topk);
    }
    /**
     * 给一篇文章 通过计算tf-idf 然后匹配相似度 文章的相似度
     * @param content
     */
    public void recommendByAriticle(String content) throws Exception {
        List<Map.Entry<String,Double>> topk=topKword.getTopk(content);//获得该篇文章的关键词
        recommendCount(topk);

    }


    public List<recommendPojo> recommendCount(List<Map.Entry<String,Double>> topk)
    {
        List<newsTopkWeight>list=newsTopkWeightMapper.getAllTopk();//获得库中所有文章的关键词
        List<recommendPojo>recommendPojos=new ArrayList<>();
        for(newsTopkWeight newsTopkWeight :list)
        {
            //数据库查出的数据转换对应的pojo
            newsTopkWeight.setContentTfidf(DataChangeUtils.stringToListMap(newsTopkWeight.getTopk()));

            //计算相似分数的核心方式
            double samilarscore= recommend.reconmmendScore(topk, newsTopkWeight.getContentTfidf(), newsTopkWeight);

            //映射成实体类 进行排序
            recommendPojo rcpj=new recommendPojo(newsTopkWeight.getNewsId(),samilarscore, newsTopkWeight);
            recommendPojos.add(rcpj);
        }
        recommendPojos.sort(new Comparator<recommendPojo>() {
            @Override
            public int compare(recommendPojo t1, recommendPojo t2) {
                return t2.getScore()>t1.getScore()?1:-1;
            }
        });
        recommendPojos=recommendPojos.subList(0,6);
//        for(recommendPojo rpojo:recommendPojos)
//        {
//            log.info(rpojo.toString());
//        }
        return recommendPojos;
    }



}
