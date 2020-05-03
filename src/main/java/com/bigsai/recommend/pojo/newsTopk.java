package com.bigsai.recommend.pojo;

import java.util.List;
import java.util.Map;

public class newsTopk {
    private int id;
    private String newsId;
    private List<Map.Entry<String,Double>> contentTfidf;
    private String topk;


    public newsTopk(String newsId){
        this.newsId=newsId;
    }
    public newsTopk(int id, String newsId,  String topk) {
        this.id = id;
        this.newsId = newsId;
        this.topk = topk;
    }
    public newsTopk(int id, String newsId, List<Map.Entry<String, Double>> contentTfidf, String topk) {
        this.id = id;
        this.newsId = newsId;
        this.contentTfidf = contentTfidf;
        this.topk = topk;
    }

    @Override
    public String toString() {
        return "newsTopk{" +
                "id='" + id + '\'' +
                ", newsId='" + newsId + '\'' +
                ", contentTfidf=" + contentTfidf +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public List<Map.Entry<String, Double>> getContentTfidf() {
        return contentTfidf;
    }

    public void setContentTfidf(List<Map.Entry<String, Double>> contentTfidf) {
        this.contentTfidf = contentTfidf;
    }

    public String getTopk() {
        return topk;
    }

    public void setTopk(String topk) {
        this.topk = topk;
    }
}
