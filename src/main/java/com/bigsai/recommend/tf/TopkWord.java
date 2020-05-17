package com.bigsai.recommend.tf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取topK关键词和对应的TF-IDF,引用TFIDF类进行操作。
 */
@Component
public class TopkWord {

    @Autowired(required = false)
    private  TFIDF TFIDF;
    public List<Map.Entry<String,Double>>getTopk(String content,int n) throws Exception {
        if(n<=0)
            throw new Exception("n非法范围");
        Map<String,Double>tfidf=TFIDF.getTFIDF(content);
        List<Map.Entry<String,Double>>topk=new ArrayList<>();
        topk.addAll(tfidf.entrySet());
        topk.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue()>o1.getValue()?1:-1;
            }
        });
        return topk.subList(0,n+1);
    }
    public List<Map.Entry<String,Double>>getTopk(String content) throws Exception {
        int n=5;
        return getTopk(content,n);
    }

}
