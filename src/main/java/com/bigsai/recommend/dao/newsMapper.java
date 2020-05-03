package com.bigsai.recommend.dao;

import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.pojo.news1;
import com.bigsai.recommend.pojo.newsTopk;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface newsMapper {

    @Select("select * from news1")
    public List<news1>getnews1();

    @Select("select * from news")
    public  List<news>getnews();

    @Select("select * from newstopk where news_id=#{newsId}")
    newsTopk getTopkById(String newsId);

    @Update("update newstopk set topk=#{topk} where news_id=#{newsId}")
    void updateTopkById(newsTopk newsTopk);

    @Insert("insert into newstopk (news_id,topk) values(#{newsId},#{topk})")
    boolean inseartTopk(newsTopk newsTopk);


    @Select("select * from newstopk")
    List<newsTopk> getAllTopk();
}
