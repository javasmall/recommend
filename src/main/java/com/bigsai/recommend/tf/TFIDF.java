package com.bigsai.recommend.tf;

import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.pojo.news;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TFIDF {
       private Logger log= LoggerFactory.getLogger(TFIDF.class);
    //只需要这些词性的词
      @Autowired(required = false)
      private newsMapper newsMapper;

      @Autowired(required = false)
      private RedisTemplate redisTemplate;

      Map<String ,Integer>allWordTimes=null;
      int contentSize;//文章总数
      Set<String> expNature = new HashSet<String>() {//过滤一些词性
        {
            add("n");add("nt");add("nz");add("nw");add("nl");add("ns");add("ng");
        }
    };
      public Map<String,Double>getTFIDF(String content)
      {
          Map<String,Double>tfidf=getTf(content);
          //tf-idf=tf * idf

          for(String word:tfidf.keySet())
          {
              tfidf.put(word,tfidf.get(word)*(double)getIDF(word));
          }
          return  tfidf;
      }
    /**
     *  单篇文章的词频
     * @param content 文本
     * @return 返回该篇文章的TF值 词频 ，并且过滤掉不需要的词频
     */
    public  Map<String, Double> getTf(String content)
    {
        content = content.replaceAll("[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", "");//去掉标点符号
        Result result= ToAnalysis.parse(content);
        List<Term>wordlist=result.getTerms();//分词结果
        Map<String,Integer>wordtime=new HashMap<>();//每个单词出现的次数
        Map<String,Double>tf=new HashMap<>();//词频
        double size=0.0;
        for(Term word:wordlist)
        {
            if(expNature.contains(word.getNatureStr()))
            {
                size++;
                if(wordtime.containsKey(word.getName()))
                {
                    wordtime.put(word.getName(),wordtime.get(word.getName())+1);
                }
                else wordtime.put(word.getName(),1);
            }
        }
        for(String key:wordtime.keySet())
        {
            double wordTf=wordtime.get(key)/size;
            tf.put(key,wordTf);
        }
        return  tf;
    }

    //返回该单词的idf
    public Double getIDF(String word)
    {
       // log.info((allWordTimes==null)+" "+allWordTimes.toString());

        if(allWordTimes==null)
        {
            allWordTimes=(Map<String, Integer>) redisTemplate.opsForValue().get("idf");
            contentSize=(int)redisTemplate.opsForValue().get("contentSize");
        }

        int count=1;
        if(allWordTimes.containsKey(word))
        count=allWordTimes.get(word)+1;

        double idf=Math.log((double) contentSize/count);
        return idf;
    }


    //计算各个单词在文章中的出现次数，遍历每一篇文章，然后将该文章的单词统计到allwordtimes中
    public  void getAllWordTime()
    {
        allWordTimes=new HashMap<>();//更新hashMap
        List<news>news=newsMapper.getnews();
        contentSize=news.size();
        for(news n:news)
        {
            try {
                analyse(n);
            }
            catch (Exception e)
            {}
        }
        //存到redis中
        redisTemplate.opsForValue().set("idf",allWordTimes);
        redisTemplate.opsForValue().set("contentSize",contentSize);

    }
     //将文章所有单词添加到总的map中。计算该单词共出现的文章数量
    private void analyse(news n) {
        String content=n.getContent();
        Result result=ToAnalysis.parse(content);
        Set<String>contentWord=new HashSet<>();
        List<Term>terms=result.getTerms();
        for(Term t:terms)
        {
            if(expNature.contains(t.getNatureStr()))
            contentWord.add(t.getName());
        }
        for(String word:contentWord)
        {
            if(allWordTimes.containsKey(word))
            {
                allWordTimes.put(word,allWordTimes.get(word)+1);
            }
            else allWordTimes.put(word,1);
        }
    }
}
