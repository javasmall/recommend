package com.bigsai.recommend.service;

import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.pojo.newsTopk;
import com.bigsai.recommend.tf.TFIDF;
import com.bigsai.recommend.tf.topKword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class newsService {
    @Autowired(required = false)
    private newsMapper newsMapper;

    @Autowired(required = false)
    private topKword topKword;


    public void insertTopk(newsTopk newsTopk)
    {
        newsTopk nt1=newsMapper.getTopkById(newsTopk.getNewsId());
        if(nt1==null)
            newsMapper.inseartTopk(newsTopk);
        else
            newsMapper.updateTopkById(newsTopk);
    }

    /**
     * 给一篇文章 通过计算tf-idf 然后匹配相似度
     * @param content
     */
    public void countValue(String content) throws Exception {
        List<Map.Entry<String,Double>> Topk=topKword.getTopk(content);
        List<newsTopk>list=newsMapper.getAllTopk();
    }
}
