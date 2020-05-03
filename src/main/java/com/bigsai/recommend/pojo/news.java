package com.bigsai.recommend.pojo;

import java.util.Date;


public class news {
    private  String id;
    private String title;
    private  String biref;
    private String content;
    private String imgurl;
    private String newUrl;
    private Date newTime;



    public news(String id, String title, String biref, String content, String imgurl, String newUrl, Date newTime) {
        this.id = id;
        this.title = title;
        this.biref = biref;
        this.content = content;
        this.imgurl = imgurl;
        this.newUrl = newUrl;
        this.newTime = newTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBiref() {
        return biref;
    }

    public void setBiref(String biref) {
        this.biref = biref;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }



    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }
}
