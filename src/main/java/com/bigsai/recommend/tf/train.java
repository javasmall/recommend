package com.bigsai.recommend.tf;


import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.pojo.newsTopk;
import com.bigsai.recommend.service.dataUtils;
import com.bigsai.recommend.service.newsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class train {
    private Logger log= LoggerFactory.getLogger(train.class);
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
    private  topKword topKword;

    //计算文章得TF-IDF值以json格式存入mysql
    public void trainContent() throws Exception {
        List<news>allnews=newsMapper.getnews();
        for(int i=0;i<allnews.size();i++)
        {
            news news1=allnews.get(i);
            String content=news1.getContent();
            List<Map.Entry<String,Double>>newstfidf=topKword.getTopk(content);
            newsTopk newsTopk=new newsTopk(news1.getId());
            newsTopk.setContentTfidf(newstfidf);
            newsTopk.setTopk(dataUtils.mapToString(newstfidf));
            try {
                newsService.insertTopk(newsTopk);
            }
            catch (Exception e)
            {
                log.info(e.toString());
            }
            newsService.insertTopk(newsTopk);


            System.out.print(news1.getTitle()+" ");
            for(Map.Entry<String,Double>m:newstfidf)
            {
                System.out.print(m.getKey()+":"+m.getValue());
            }
            System.out.println();
        }
    }

    //获取所有得单词词频
    public void trainAllwordTimes()
    {
        tfidf.getAllWordTime();
    }
}
