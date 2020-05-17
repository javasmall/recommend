package com.bigsai.recommend.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class users {
    private int id;
    private String username;
    private String preList;
    private Date lastLogin;
    private Map<String,Double> preferList;
    public users(){}

    public users(int id, String username, String preList, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.preList = preList;
        this.lastLogin = lastLogin;
    }
    public users(String username, String preList, Date lastLogin) {
        this.username = username;
        this.preList = preList;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreList() {
        return preList;
    }

    public void setPreList(String preList) {
        this.preList = preList;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }



    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", preList='" + preList + '\'' +
                ", lastLogin=" + lastLogin +
                ", preferList=" + preferList +
                '}';
    }

    public void setPreferList(Map<String, Double> preferList) {
        this.preferList = preferList;
    }
}
