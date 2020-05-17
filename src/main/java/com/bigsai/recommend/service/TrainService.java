package com.bigsai.recommend.service;


import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.pojo.newsTopkWeight;
import com.bigsai.recommend.tf.TFIDF;
import com.bigsai.recommend.utils.DataChangeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TrainService {
    private Logger log= LoggerFactory.getLogger(TrainService.class);
    //全部数据重新计算
    @Autowired(required = false)
    private newsMapper newsMapper;

    @Autowired(required = false)
    private TFIDF tfidf;

    @Autowired(required = false)
    private newsService newsService;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    @Autowired(required = false)
    private com.bigsai.recommend.tf.TopkWord topKword;

    //计算文章得TF-IDF值以json格式存入mysql
    public void trainContent() throws Exception {
        List<news>allnews=newsMapper.getnews();
        for(int i=0;i<allnews.size();i++)//插入、更新对应的topk 和 newsweight
        {
            news news1=allnews.get(i);
            String content=news1.getContent();
            List<Map.Entry<String,Double>>newstfidf=topKword.getTopk(content);
            newsTopkWeight newsTopkWeight =new newsTopkWeight(news1.getId());
            newsTopkWeight.setContentTfidf(newstfidf);
            newsTopkWeight.setTopk(DataChangeUtils.ListmapToString(newstfidf));
            newsTopkWeight.setStar(0);
            newsTopkWeight.setWeight(1.0);

            try {//没有则插入，有则更新
                newsService.insertorUpdateTopkWeight(newsTopkWeight);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                log.info(e.toString());
            }


        }
    }

    //获取所有得单词词频
    public void trainAllwordTimes()
    {
        tfidf.getAllWordTime();
    }
}
