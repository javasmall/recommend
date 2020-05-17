package com.bigsai.recommend.dao;

import com.bigsai.recommend.pojo.newsTopkWeight;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface newsTopkWeightMapper {

    @Select("select * from news_topk_weight where news_id=#{newsId}")
    newsTopkWeight getTopkById(String newsId);

    @Select("select * from news_topk_weight")
    List<newsTopkWeight> getAllTopk();


    @Select("select * from news_topk_weight where news_id=#{newsId}")
    newsTopkWeight getTopkWeightById(String newsId);

    @Insert("insert into news_topk_weight (news_id,topk,weight,start) values(#{newsId},#{topk},#{weight},#{star})")
    boolean insertNewsTopkWeight(newsTopkWeight newsTopkWeight);

    @Update("update news_topk_weight set topk=#{topk},weight=#{weight},star=#{star} where news_id=#{newsId}")
    boolean updateTopkWeightById(newsTopkWeight newsTopkWeight);


    @Select("select news_id,weight from news_topk_weight")
    List<Map<String, Object>> getAllNewsIdAndWeight();

    void updateWeightByNewsId(@Param("map") Map<String, Double> newsidAndWeghtMap);
}