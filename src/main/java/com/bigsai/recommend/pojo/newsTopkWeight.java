package com.bigsai.recommend.pojo;

import java.util.List;
import java.util.Map;

public class newsTopkWeight {
    private int id;
    private String newsId;
    private List<Map.Entry<String,Double>> contentTfidf;
    private String topk;
    private double weight;
    private int star;

    public newsTopkWeight(int id, String newsId, String topk, double weight, int star) {
        this.id = id;
        this.newsId = newsId;
        this.topk = topk;
        this.weight = weight;
        this.star = star;
    }




    public newsTopkWeight(String newsId){
        this.newsId=newsId;
    }
    public newsTopkWeight(int id, String newsId, String topk) {
        this.id = id;
        this.newsId = newsId;
        this.topk = topk;
    }
    public newsTopkWeight(int id, String newsId, List<Map.Entry<String, Double>> contentTfidf, String topk) {
        this.id = id;
        this.newsId = newsId;
        this.contentTfidf = contentTfidf;
        this.topk = topk;
    }

    @Override
    public String toString() {
        return "newsTopkWeight{" +
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
