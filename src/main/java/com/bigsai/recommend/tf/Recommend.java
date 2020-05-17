package com.bigsai.recommend.tf;

import com.bigsai.recommend.dao.newsTopkWeightMapper;
import com.bigsai.recommend.pojo.newsTopkWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public  class Recommend
{

    @Autowired(required = false)
    newsTopkWeightMapper newsweightMapper;
    public double reconmmendScore(List<Map.Entry<String, Double>> list1, List<Map.Entry<String, Double>> list2, newsTopkWeight newsTopkWeight)
    {
        double ttidfcount=countTfidfscore(list1,list2);//tfidf 的值
        double otherWeight=countOtherWeight(newsTopkWeight);
        return ttidfcount*otherWeight;
    }

    /**
     * 通过时间  weight权重 star(点赞数量一系列数据进行计算)
     * @param newsTopkWeight
     * @return 计算的一个数值然后相乘
     */
    private double countOtherWeight(newsTopkWeight newsTopkWeight) {
        double weight=newsTopkWeight.getWeight();
        int star=newsTopkWeight.getStar();
        double score=weight*Math.log(Math.E+star);
        return  score;
    }


    /**
     * 两篇文章的关键词和对应tfidf。
     * @param list1 当前文章
     * @param list2 比对新闻
     * @return 相似度
     */
    public  double countTfidfscore(List<Map.Entry<String, Double>> list1, List<Map.Entry<String, Double>> list2)
    {
        double samilarscore=0.0;
        for(int i=0;i<list1.size();i++)//遍历其中一个文章的关键词
        {
            Map.Entry<String,Double>team=list1.get(i);//该关键词和另一篇文章的所有关键词比较是否有相同
            //比较
            for(Map.Entry<String,Double> entry:list2)
            {
                if(entry.getKey().equals(team.getKey()))
                {
                    //samilarscore+=(double)entry.getValue()*(double)team.getValue();
                    // 会抛出异常java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Double
                    Object object1=entry.getValue();
                    Object object2=team.getValue();
                    double a=Double.parseDouble(object1.toString());
                    double b=Double.parseDouble(object2.toString());
                    samilarscore+=a*b;
                    break;//跳出当前循环
                }
            }
        }
        return  samilarscore;
    }

}
