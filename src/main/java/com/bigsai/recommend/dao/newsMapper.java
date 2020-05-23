package com.bigsai.recommend.dao;

import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.pojo.newsOriginal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface newsMapper {

    @Select("select * from newsOriginal")
    public List<newsOriginal>getnews1();

    @Select("select * from news")
    public  List<news>getnews();

    @Select("select * from  newsOriginal")
    List<newsOriginal>getOriginalNew();


}
