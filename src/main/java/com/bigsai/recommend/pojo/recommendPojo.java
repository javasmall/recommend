package com.bigsai.recommend.pojo;

/**
 * 不对应数据库的实体，一个临时的Entity
 */
public class recommendPojo {

    private String newsId;
    private Double score;
    private newsTopkWeight newsTopkWeight;

    public recommendPojo(String newsId, Double score, newsTopkWeight newsTopkWeight) {
        this.newsId = newsId;
        this.score = score;
        this.newsTopkWeight = newsTopkWeight;
    }
    @Override
    public String toString() {
        return "recommendPojo{" +
                "newsId='" + newsId + '\'' +
                ", score=" + score +
                ", newsTopkWeight=" + newsTopkWeight +
                '}';
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public newsTopkWeight getNewsTopkWeight() {
        return newsTopkWeight;
    }

    public void setNewsTopkWeight(newsTopkWeight newsTopkWeight) {
        this.newsTopkWeight = newsTopkWeight;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
