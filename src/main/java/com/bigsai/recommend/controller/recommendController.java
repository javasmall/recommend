package com.bigsai.recommend.controller;

import com.bigsai.recommend.pojo.recommendPojo;
import com.bigsai.recommend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class recommendController {

    @Autowired(required = false)
    NewsService newsService;

    @GetMapping("recommend/user/{username}")
    public List<recommendPojo> recommendByusername(@PathVariable("username")  String username)
    {
        //newsService.recommendByusername(username);
        return newsService.recommendByusername(username);
    }
    @GetMapping("recommend/news/{newsId}")
    public List<recommendPojo>recomendbynewsId(@PathVariable("newsId")String newsId)
    {
        return newsService.recommendBynewsId(newsId);
    }
}
